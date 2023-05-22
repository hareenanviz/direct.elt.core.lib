package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.spark.api.java.function.Function2;

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
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentFunction2<T, I, R, E> implements Function2<T, I, R> {
	private static final long serialVersionUID = 1L;

	private ValidationViolationListener<I> validationViolationListener;
	protected LinkedHashMap<String, AnvizentDataType> structure;
	protected LinkedHashMap<String, AnvizentDataType> newStructure;
	protected ConfigBean configBean;
	protected MappingConfigBean mappingConfigBean;
	private ArrayList<AnvizentAccumulator> accumulators;
	protected Long retryDelay;
	protected int maxRetryCount;
	private BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction;
	private JobDetails jobDetails;

	public BaseAnvizentFunction2(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<I> validationViolationListener,
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
	public R call(T row_1, I row_2) throws Exception {
		accumulate(StatsCategory.IN, row_2, null, null);
		RecordProcessingException recordProcessingException = null;
		int i = 0;
		do {
			recordProcessingException = null;

			try {
				R outputRow = process(row_1, row_2);
				accumulate(StatsCategory.OUT, row_2, outputRow, null);
				accumulate(StatsCategory.OTHER, row_2, outputRow, null);

				return outputRow;
			} catch (ValidationViolationException validationViolationException) {
				// TODO
				if (validationViolationListener == null) {
					accumulate(StatsCategory.OUT, row_2, null, validationViolationException);
					throw validationViolationException;
				} else {
					validationViolationListener.validationViolated(row_2, validationViolationException);
					break;
				}
			} catch (RecordProcessingException processingException) {
				recordProcessingException = processingException;

				if (retryDelay != null && retryDelay > 0) {
					Thread.sleep(retryDelay);
				}
			} catch (DataCorruptedException dataCorruptedException) {
				accumulate(StatsCategory.ERROR, row_2, null, dataCorruptedException);
				if (errorHandlerSinkFunction != null) {
					errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row_1, row_2, dataCorruptedException));
					break;
				} else {
					throw dataCorruptedException;
				}
			}
		} while (++i < maxRetryCount);

		if (recordProcessingException != null) {
			accumulate(StatsCategory.ERROR, row_2, null, recordProcessingException);

			if (recordProcessingException.getCause().getClass().equals(DataCorruptedException.class)) {
				if (errorHandlerSinkFunction != null) {
					errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row_1, row_2,
					        (DataCorruptedException) recordProcessingException.getCause()));
				} else {
					throw recordProcessingException;
				}
			} else {
				throw recordProcessingException;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private void accumulate(StatsCategory accumulatorKey, I inputRow, R outputRow, AnvizentFunctionException exception) {
		if (accumulators == null) {
			return;
		}

		for (AnvizentAccumulator accumulator : accumulators) {
			if (accumulator != null) {
				try {
					accumulator.getDoubleAccumulator().add(
					        accumulator.getAnvizent2StatsCalculator().getValue(accumulatorKey, accumulator.getStatsName(), inputRow, outputRow, exception));
				} catch (AccumulationException accumulationException) {
					// TODO add listeners
					accumulationException.printStackTrace();
				}
			}
		}
	}

	public abstract R process(T row_1, I row_2) throws ValidationViolationException, RecordProcessingException, DataCorruptedException;

	protected abstract BaseAnvizentErrorSetter2<T, I, E> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException;
}
