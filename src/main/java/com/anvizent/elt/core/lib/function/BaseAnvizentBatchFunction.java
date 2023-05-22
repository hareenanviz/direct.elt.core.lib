package com.anvizent.elt.core.lib.function;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.spark.TaskContext;
import org.apache.spark.api.java.function.VoidFunction;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.BatchTrackingCountConfigBean;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.exception.ImproperValidationException;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.InvalidRelationException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;
import com.anvizent.elt.core.lib.stats.beans.DBWrittenStats;
import com.arangodb.ArangoDBException;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentBatchFunction<T, E> implements VoidFunction<Iterator<T>> {

	private static final long serialVersionUID = 1L;

	private ValidationViolationListener<T> validationViolationListener;
	protected LinkedHashMap<String, AnvizentDataType> structure;
	protected LinkedHashMap<String, AnvizentDataType> newStructure;
	private int maxRetryCount;
	private Long retryDelay;
	private int initMaxRetryCount;
	private Long initRetryDelay;
	private int destoryMaxRetryCount;
	private Long destoryRetryDelay;
	protected ConfigBean configBean;
	protected MappingConfigBean mappingConfigBean;
	/**
	 * batch size 0 means, all batch
	 */
	protected long batchSize;
	private ArrayList<AnvizentAccumulator> accumulators;
	private BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction;
	private JobDetails jobDetails;

	private HashMap<Integer, HashMap<Long, Tuple2<Integer, Long>>> batchData = new HashMap<>();

	public BaseAnvizentBatchFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<T> validationViolationListener, Integer maxRetryCount,
	        Long retryDelay, Integer initMaxRetryCount, Long initRetryDelay, Integer destoryMaxRetryCount, Long destoryRetryDelay, long batchSize,
	        ArrayList<AnvizentAccumulator> accumulators, BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction, JobDetails jobDetails)
	        throws InvalidRelationException, InvalidArgumentsException {
		if (this instanceof MappingFunction) {
			throw new InvalidRelationException("'" + this.getClass().getName() + "' can not be a '" + MappingFunction.class.getName() + "' as its a child of '"
			        + BaseAnvizentBatchFunction.class.getName() + "'");
		}

		LogUtil.printUsage(configBean);

		this.validationViolationListener = validationViolationListener;
		this.structure = structure;
		this.newStructure = newStructure;
		this.maxRetryCount = maxRetryCount;
		this.retryDelay = retryDelay;
		this.initMaxRetryCount = initMaxRetryCount;
		this.initRetryDelay = initRetryDelay;
		this.destoryMaxRetryCount = destoryMaxRetryCount;
		this.destoryRetryDelay = destoryRetryDelay;
		this.configBean = configBean;
		this.mappingConfigBean = mappingConfigBean;
		this.batchSize = batchSize;
		this.accumulators = accumulators;
		this.errorHandlerSinkFunction = errorHandlerSinkFunction;
		this.jobDetails = jobDetails;
	}

	@Override
	public void call(Iterator<T> rows) throws Exception {

		BatchTrackingCountConfigBean trackingCounts = new BatchTrackingCountConfigBean(0L, 0L, 0L);

		init(rows, trackingCounts);
		processRows(rows, trackingCounts);
		destory(rows, trackingCounts);
		print();
	}

	private void print() {
		System.out.print("\n\n\n\n\n\n");
		long sqlTotal = 0l;
		int rowTotal = 0;

		System.out.println("================================================");
		System.out.print("Batch number    Batch execute time    no of rows\n");
		for (Entry<Integer, HashMap<Long, Tuple2<Integer, Long>>> partitionBatchData : this.batchData.entrySet()) {
			System.out.printf("=====================%04d=======================\n", partitionBatchData.getKey());
			for (Entry<Long, Tuple2<Integer, Long>> batchData : partitionBatchData.getValue().entrySet()) {
				System.out.printf("         %03d    %018d           %03d\n", batchData.getKey(), batchData.getValue()._2(), batchData.getValue()._1());
				sqlTotal += batchData.getValue()._2();
				rowTotal += batchData.getValue()._1();
			}
			System.out.println("================================================");
		}

		System.out.printf("Total           %018d           %03d\n", sqlTotal, rowTotal);
		System.out.println("================================================\n\n\n\n\n\n");
	}

	private void init(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts) throws ValidationViolationException, Exception {
		Exception thrownException = null;
		int i = 0;

		do {
			try {
				thrownException = null;

				LogUtil.printUsage(configBean);
				onCallStart(rows, trackingCounts);
			} catch (Exception exception) {
				if (exception instanceof RecordProcessingException) {
					thrownException = exception;

					if (initRetryDelay != null && initRetryDelay > 0) {
						Thread.sleep(initRetryDelay);
					}
				} else {
					throw exception;
				}
			}

		} while (++i < initMaxRetryCount);

		if (thrownException != null) {
			throw new ValidationViolationException(thrownException.getMessage(), thrownException);
		}
	}

	private void destory(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts) throws ValidationViolationException, Exception {
		Exception thrownException = null;
		int i = 0;

		do {
			try {
				thrownException = null;

				onCallEnd(rows, trackingCounts);
			} catch (Exception exception) {
				if (exception instanceof RecordProcessingException) {
					thrownException = exception;

					if (destoryRetryDelay != null && destoryRetryDelay > 0) {
						Thread.sleep(destoryRetryDelay);
					}
				} else {
					throw exception;
				}
			}
		} while (++i < destoryMaxRetryCount);

		if (thrownException != null) {
			throw new ValidationViolationException(thrownException.getMessage(), thrownException);
		}
	}

	private void processRows(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts) throws Exception {

		ArrayList<T> batchedRows = new ArrayList<T>();

		while (rows.hasNext()) {
			T row = rows.next();
			batchedRows.add(row);

			accumulate(StatsCategory.IN, rows, row, null, null);

			process(row, trackingCounts, batchedRows);

			if (batchSize > 0 && trackingCounts.getCount() != 0 && trackingCounts.getCurrentBatchCount() > batchSize) {
				LogUtil.printUsage(configBean);

				batch(rows, trackingCounts, batchedRows);
			}
		}

		if (trackingCounts.getCurrentBatchCount() != 0) {
			batch(rows, trackingCounts, batchedRows);
		}
	}

	private void process(T row, BatchTrackingCountConfigBean trackingCounts, ArrayList<T> batchedRows) throws Exception {
		ArrayList<T> rows = new ArrayList<T>();
		rows.add(row);

		RecordProcessingException recordProcessingException = null;
		int i = 0;

		do {
			try {
				recordProcessingException = null;

				boolean processed = process(row, batchedRows);
				if (processed) {
					trackingCounts.addCount(1L);
					trackingCounts.addCurrentBatchCount(1L);
				}

				return;
			} catch (ValidationViolationException validationViolationException) {
				if (validationViolationListener == null) {
					accumulate(StatsCategory.ERROR, rows.iterator(), null, null, validationViolationException);
					throw validationViolationException;
				} else {
					validationViolationListener.validationViolated(rows.iterator(), validationViolationException);
					break;
				}
			} catch (RecordProcessingException processingException) {
				recordProcessingException = processingException;

				if (destoryRetryDelay != null && destoryRetryDelay > 0) {
					Thread.sleep(destoryRetryDelay);
				}

				trackingCounts.subCount(1L);
				trackingCounts.subCurrentBatchCount(1L);
			} catch (DataCorruptedException dataCorruptedException) {
				accumulate(StatsCategory.ERROR, rows.iterator(), null, null, dataCorruptedException);
				if (errorHandlerSinkFunction != null) {
					errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, dataCorruptedException));
					break;
				} else {
					throw dataCorruptedException;
				}
			}
		} while (++i < destoryMaxRetryCount);

		if (recordProcessingException != null) {
			accumulate(StatsCategory.ERROR, rows.iterator(), null, null, recordProcessingException);

			if (recordProcessingException.getCause().getClass().equals(DataCorruptedException.class)) {
				if (errorHandlerSinkFunction != null) {
					errorHandlerSinkFunction
					        .call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, (DataCorruptedException) recordProcessingException.getCause()));
				} else {
					throw recordProcessingException;
				}
			} else {
				throw recordProcessingException;
			}
		}
	}

	private void batch(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts, ArrayList<T> batchedRows)
	        throws RecordProcessingException, ValidationViolationException, Exception {
		RecordProcessingException recordProcessingException = null;
		int i = 0;

		do {
			try {
				recordProcessingException = null;

				batch(trackingCounts, rows, batchedRows);
				break;
			} catch (ValidationViolationException validationViolationException) {
				if (validationViolationListener == null) {
					accumulate(StatsCategory.ERROR, rows, null, null, validationViolationException);
					throw validationViolationException;
				} else {
					validationViolationListener.validationViolated(rows, validationViolationException);
					break;
				}
			} catch (RecordProcessingException processingException) {
				i++;
				recordProcessingException = processingException;
				retryBatch(trackingCounts, rows, batchedRows, recordProcessingException, i);
			} catch (DataCorruptedException dataCorruptedException) {
				accumulate(StatsCategory.ERROR, rows, null, null, dataCorruptedException);
				if (errorHandlerSinkFunction != null) {
					for (T row : batchedRows) {
						errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row, dataCorruptedException));
					}
					break;
				} else {
					throw dataCorruptedException;
				}
			}
		} while (i < maxRetryCount);

		if (recordProcessingException != null) {
			accumulate(StatsCategory.ERROR, rows, null, null, recordProcessingException);

			if ((recordProcessingException.getCause() instanceof BatchUpdateException
			        || recordProcessingException.getCause() instanceof SQLIntegrityConstraintViolationException)
			        || (recordProcessingException.getCause().getClass().equals(ArangoDBException.class)
			                && (recordProcessingException.getCause().getMessage().contains("unique constraint violated")))
			        || recordProcessingException.getCause().getClass().equals(DataCorruptedException.class)) {
				if (errorHandlerSinkFunction != null) {
					for (T row : batchedRows) {
						errorHandlerSinkFunction.call(getBaseAnvizentErrorRowSetter().convertRow(jobDetails, row,
						        new DataCorruptedException(recordProcessingException.getMessage(), recordProcessingException.getCause())));
					}
				} else {
					throw recordProcessingException;
				}
			} else {
				throw recordProcessingException;
			}

			batchedRows.clear();
		}
	}

	private void batch(BatchTrackingCountConfigBean trackingCounts, Iterator<T> rows, ArrayList<T> batchedRows)
	        throws RecordProcessingException, ValidationViolationException, Exception {
		HashMap<Long, Tuple2<Integer, Long>> batchData = this.batchData.get(TaskContext.getPartitionId());
		if (batchData == null) {
			batchData = new HashMap<>();
			this.batchData.put(TaskContext.getPartitionId(), batchData);
		}

		beforeBatch(rows);
		trackingCounts.addBatchNumber(1L);

		Date date = new Date();
		int size = batchedRows.size();
		DBWrittenStats dbWrittenStats = onBatch(rows, trackingCounts.getBatchNumber(), trackingCounts.getCurrentBatchCount(), batchedRows);
		batchData.put(trackingCounts.getBatchNumber(), new Tuple2<>(size, new Date().getTime() - date.getTime()));

		onBatchSuccess(trackingCounts.getBatchNumber(), trackingCounts.getCurrentBatchCount(), batchedRows);
		afterBatch(rows, batchedRows);
		batchedRows.clear();
		trackingCounts.setCurrentBatchCount(0L);

		accumulate(StatsCategory.OUT, rows, null, dbWrittenStats, null);
	}

	private void retryBatch(BatchTrackingCountConfigBean trackingCounts, Iterator<T> rows, ArrayList<T> batchedRows,
	        RecordProcessingException recordProcessingException, int i) throws Exception {
		if (retryDelay != null && retryDelay > 0) {
			Thread.sleep(retryDelay);
		}

		onBatchFail(trackingCounts.getBatchNumber(), trackingCounts.getCurrentBatchCount(), batchedRows);
		afterBatch(rows, batchedRows);

		trackingCounts.subBatchNumber(1L);
		trackingCounts.setCurrentBatchCount(0L);
		trackingCounts.subCount(trackingCounts.getCurrentBatchCount());

		if (i < maxRetryCount) {
			reProcess(batchedRows, trackingCounts);
		}
	}

	private void reProcess(ArrayList<T> batchedRows, BatchTrackingCountConfigBean trackingCounts) throws Exception {
		for (T row : batchedRows) {
			process(row, trackingCounts, batchedRows);
		}
	}

	@SuppressWarnings("unchecked")
	private void accumulate(StatsCategory accumulatorKey, Iterator<T> rows, T inputRow, Object output, AnvizentFunctionException exception) {
		if (accumulators == null) {
			return;
		}

		for (AnvizentAccumulator accumulator : accumulators) {
			if (accumulator != null) {
				try {
					accumulator.getDoubleAccumulator().add(accumulator.getBaseSQLBatchStatsCalculator().getValue(accumulatorKey, accumulator.getStatsName(),
					        rows, inputRow, output, exception));
				} catch (AccumulationException accumulationException) {
					// TODO add listeners
					accumulationException.printStackTrace();
				}
			}
		}
	}

	public abstract void onCallStart(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts) throws ImproperValidationException, Exception;

	public abstract void onCallEnd(Iterator<T> rows, BatchTrackingCountConfigBean trackingCounts) throws Exception;

	public abstract void beforeBatch(Iterator<T> rows) throws Exception;

	public abstract void afterBatch(Iterator<T> rows, ArrayList<T> batchedRows) throws Exception;

	public abstract DBWrittenStats onBatch(Iterator<T> rows, long batchNumber, long currentBatchCount, ArrayList<T> batchedRows)
	        throws RecordProcessingException, ValidationViolationException, DataCorruptedException;

	public abstract boolean process(T row, ArrayList<T> batchedRows) throws Exception;

	public abstract void onBatchSuccess(long batchNumber, long currentBatchCount, ArrayList<T> batchedRows) throws Exception;

	public abstract void onBatchFail(long batchNumber, long currentBatchCount, ArrayList<T> batchedRows) throws Exception;

	protected abstract BaseAnvizentErrorSetter<T, E> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException;
}
