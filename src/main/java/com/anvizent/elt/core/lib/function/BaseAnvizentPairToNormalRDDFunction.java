package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.spark.api.java.function.FlatMapFunction;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentPairToNormalRDDFunction<K, V, T, E> implements FlatMapFunction<Tuple2<K, V>, T> {

	private static final long serialVersionUID = 1L;

	private ValidationViolationListener<V> validationViolationListener;
	protected LinkedHashMap<String, AnvizentDataType> structure;
	protected LinkedHashMap<String, AnvizentDataType> newStructure;
	protected ConfigBean configBean;
	protected MappingConfigBean mappingConfigBean;
	private ArrayList<AnvizentAccumulator> accumulators;
	protected Long retryDelay;
	protected int maxRetryCount;
	private BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction;
	private JobDetails jobDetails;

	public BaseAnvizentPairToNormalRDDFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<V> validationViolationListener,
	        ArrayList<AnvizentAccumulator> accumulators, BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction, JobDetails jobDetails)
	        throws InvalidArgumentsException {
		LogUtil.printUsage(configBean);

		this.configBean = configBean;
		this.mappingConfigBean = mappingConfigBean;
		this.structure = structure;
		this.newStructure = newStructure;
		this.validationViolationListener = validationViolationListener;
		this.accumulators = accumulators;
		this.errorHandlerSinkFunction = errorHandlerSinkFunction;
		this.jobDetails = jobDetails;

		if (this instanceof MappingFunction) {
			retryDelay = mappingConfigBean.getRetryDelay();
			maxRetryCount = mappingConfigBean.getMaxRetryCount();
		} else {
			retryDelay = configBean.getRetryDelay();
			maxRetryCount = configBean.getMaxRetryCount();
		}
	}

	@Override
	public Iterator<T> call(Tuple2<K, V> row) throws Exception {
		ArrayList<T> newRows = new ArrayList<T>(1);

		accumulate(StatsCategory.IN, row == null ? null : row._2(), null, null);

		if (row == null) {
			return newRows.iterator();
		} else {
			V inputRow = row._2();
			RecordProcessingException recordProcessingException = null;
			int i = 0;

			do {
				recordProcessingException = null;

				try {
					T newRow = process(inputRow);
					accumulate(StatsCategory.OUT, inputRow, newRow, null);

					newRows.add(newRow);

					return newRows.iterator();
				} catch (ValidationViolationException validationViolationException) {
					// TODO
					if (validationViolationListener == null) {
						accumulate(StatsCategory.ERROR, inputRow, null, validationViolationException);
						throw validationViolationException;
					} else {
						validationViolationListener.validationViolated(inputRow, validationViolationException);
						break;
					}
				} catch (RecordProcessingException processingException) {
					recordProcessingException = processingException;

					if (retryDelay != null && retryDelay > 0) {
						Thread.sleep(retryDelay);
					}
				} catch (DataCorruptedException dataCorruptedException) {
					accumulate(StatsCategory.ERROR, inputRow, null, dataCorruptedException);
					if (errorHandlerSinkFunction != null) {
						errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, dataCorruptedException));
						break;
					} else {
						throw dataCorruptedException;
					}
				}
			} while (++i < maxRetryCount);

			if (recordProcessingException != null) {
				accumulate(StatsCategory.ERROR, inputRow, null, recordProcessingException);

				if (recordProcessingException.getCause().getClass().equals(DataCorruptedException.class)) {
					if (errorHandlerSinkFunction != null) {
						errorHandlerSinkFunction.call(
						        getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, (DataCorruptedException) recordProcessingException.getCause()));
					} else {
						throw recordProcessingException;
					}
				} else {
					throw recordProcessingException;
				}
			}

			return newRows.iterator();
		}
	}

	@SuppressWarnings("unchecked")
	private void accumulate(StatsCategory accumulatorKey, V inputRow, T outputRow, AnvizentFunctionException exception) {
		if (accumulators == null) {
			return;
		}

		for (AnvizentAccumulator accumulator : accumulators) {
			if (accumulator != null) {
				try {
					accumulator.getDoubleAccumulator()
					        .add(accumulator.getStatsCalculator().getValue(accumulatorKey, accumulator.getStatsName(), inputRow, outputRow, exception));
				} catch (AccumulationException accumulationException) {
					// TODO add listeners
					accumulationException.printStackTrace();
				}
			}
		}
	}

	public abstract T process(V inputRow) throws ValidationViolationException, RecordProcessingException, DataCorruptedException;

	protected abstract BaseAnvizentErrorSetter<Tuple2<K, V>, E> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException;
}
