package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.spark.api.java.function.PairFunction;

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
public abstract class BaseAnvizentPairFunction<T, K, V, E> implements PairFunction<T, K, V> {

	private static final long serialVersionUID = 1L;

	private ValidationViolationListener<T> validationViolationListener;
	protected LinkedHashMap<String, AnvizentDataType> structure;
	protected LinkedHashMap<String, AnvizentDataType> newStructure;
	protected ConfigBean configBean;
	protected MappingConfigBean mappingConfigBean;
	private ArrayList<AnvizentAccumulator> accumulators;
	protected Long retryDelay;
	protected int maxRetryCount;
	private BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction;
	private JobDetails jobDetails;

	public BaseAnvizentPairFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<T> validationViolationListener,
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
	public Tuple2<K, V> call(T row) throws Exception {
		accumulate(StatsCategory.IN, row, null, null);

		if (row == null) {
			return null;
		} else {
			RecordProcessingException recordProcessingException = null;
			int i = 0;

			do {
				recordProcessingException = null;

				try {
					Tuple2<K, V> outputRow = process(row);
					accumulate(StatsCategory.OUT, row, outputRow, null);

					return outputRow;
				} catch (ValidationViolationException validationViolationException) {
					// TODO
					if (validationViolationListener == null) {
						accumulate(StatsCategory.ERROR, row, null, validationViolationException);
						throw validationViolationException;
					} else {
						validationViolationListener.validationViolated(row, validationViolationException);
						break;
					}
				} catch (RecordProcessingException processingException) {
					recordProcessingException = processingException;

					if (retryDelay != null && retryDelay > 0) {
						Thread.sleep(retryDelay);
					}
				} catch (DataCorruptedException dataCorruptedException) {
					// TODO Error handling
					accumulate(StatsCategory.ERROR, row, null, dataCorruptedException);
					if (errorHandlerSinkFunction != null) {
						errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, dataCorruptedException));
						break;
					} else {
						throw dataCorruptedException;
					}
				}
			} while (++i < maxRetryCount);

			if (recordProcessingException != null) {
				accumulate(StatsCategory.ERROR, row, null, recordProcessingException);
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

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void accumulate(StatsCategory accumulatorKey, T inputRow, Tuple2<K, V> outputRow, AnvizentFunctionException exception) {
		if (accumulators == null) {
			return;
		}

		for (AnvizentAccumulator accumulator : accumulators) {
			if (accumulator != null) {
				try {
					accumulator.getDoubleAccumulator().add(
					        accumulator.getMapToPairStatsCalculator().getValue(accumulatorKey, accumulator.getStatsName(), inputRow, outputRow, exception));
				} catch (AccumulationException accumulationException) {
					// TODO add listeners
					accumulationException.printStackTrace();
				}
			}
		}
	}

	public abstract Tuple2<K, V> process(T row) throws ValidationViolationException, RecordProcessingException, DataCorruptedException;

	protected abstract BaseAnvizentErrorSetter<T, E> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException;
}
