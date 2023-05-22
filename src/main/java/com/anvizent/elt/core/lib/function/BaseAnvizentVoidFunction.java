package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.spark.api.java.function.VoidFunction;

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
import com.anvizent.elt.core.lib.exception.InvalidRelationException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;
import com.anvizent.elt.core.lib.stats.beans.WrittenRow;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentVoidFunction<T, E> implements VoidFunction<T> {

	private static final long serialVersionUID = 1L;

	private ValidationViolationListener<T> validationViolationListener;
	protected LinkedHashMap<String, AnvizentDataType> structure;
	protected LinkedHashMap<String, AnvizentDataType> newStructure;
	protected ConfigBean configBean;
	protected MappingConfigBean mappingConfigBean;
	private ArrayList<AnvizentAccumulator> accumulators;
	private BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction;
	private JobDetails jobDetails;

	public BaseAnvizentVoidFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<T> validationViolationListener,
	        ArrayList<AnvizentAccumulator> accumulators, BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction, JobDetails jobDetails)
	        throws InvalidRelationException {
		LogUtil.printUsage(configBean);

		if (this instanceof MappingFunction) {
			throw new InvalidRelationException("'" + this.getClass().getName() + "' can not be a '" + MappingFunction.class.getName() + "' as its a child of '"
			        + BaseAnvizentBatchFunction.class.getName() + "'");
		}

		this.configBean = configBean;
		this.mappingConfigBean = mappingConfigBean;
		this.structure = structure;
		this.newStructure = newStructure;
		this.validationViolationListener = validationViolationListener;
		this.accumulators = accumulators;
		this.errorHandlerSinkFunction = errorHandlerSinkFunction;
		this.jobDetails = jobDetails;
	}

	@Override
	public void call(T row) throws Exception {
		if (row == null) {
			return;
		} else {
			accumulate(StatsCategory.IN, row, null, null);
			RecordProcessingException recordProcessingException = null;
			int i = 0;

			do {
				recordProcessingException = null;

				try {
					WrittenRow writtenRow = process(row);
					accumulate(StatsCategory.OUT, row, writtenRow, null);
					return;
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

					if (configBean.getRetryDelay() != null && configBean.getRetryDelay() > 0) {
						Thread.sleep(configBean.getRetryDelay());
					}
				} catch (DataCorruptedException dataCorruptedException) {
					accumulate(StatsCategory.ERROR, row, null, dataCorruptedException);
					if (errorHandlerSinkFunction != null) {
						errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, dataCorruptedException));
						break;
					} else {
						throw dataCorruptedException;
					}
				}
			} while (++i < configBean.getMaxRetryCount());

			if (recordProcessingException != null) {
				accumulate(StatsCategory.ERROR, row, null, recordProcessingException);

				if (recordProcessingException.getCause() != null && recordProcessingException.getCause().getClass().equals(DataCorruptedException.class)) {
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
		}
	}

	@SuppressWarnings("unchecked")
	private void accumulate(StatsCategory accumulatorKey, T inputRow, WrittenRow writtenRow, AnvizentFunctionException exception) {
		if (accumulators == null) {
			return;
		}

		for (AnvizentAccumulator accumulator : accumulators) {
			if (accumulator != null) {
				try {
					accumulator.getDoubleAccumulator()
					        .add(accumulator.getVoidStatsCalculator().getValue(accumulatorKey, accumulator.getStatsName(), inputRow, writtenRow, exception));
				} catch (AccumulationException accumulationException) {
					// TODO add listners
					accumulationException.printStackTrace();
				}
			}
		}
	}

	public abstract WrittenRow process(T row) throws ValidationViolationException, RecordProcessingException, DataCorruptedException;

	protected abstract BaseAnvizentErrorSetter<T, E> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException;

}
