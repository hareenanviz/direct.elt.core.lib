package com.anvizent.elt.core.lib.config.bean;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class BatchTrackingCountConfigBean {

	private Long batchNumber;
	private Long currentBatchCount;
	private Long count;

	public Long getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Long batchNumber) {
		this.batchNumber = batchNumber;
	}

	public void addBatchNumber(Long batchNumber) {
		this.batchNumber += batchNumber;
	}

	public void subBatchNumber(Long batchNumber) {
		this.batchNumber -= batchNumber;
	}

	public Long getCurrentBatchCount() {
		return currentBatchCount;
	}

	public void setCurrentBatchCount(Long currentBatchCount) {
		this.currentBatchCount = currentBatchCount;
	}

	public void addCurrentBatchCount(Long currentBatchCount) {
		this.currentBatchCount += currentBatchCount;
	}

	public void subCurrentBatchCount(Long currentBatchCount) {
		this.currentBatchCount -= currentBatchCount;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void addCount(Long count) {
		this.count += count;
	}

	public void subCount(Long count) {
		this.count -= count;
	}

	public BatchTrackingCountConfigBean(Long batchNumber, Long currentBatchCount, Long count) {
		this.batchNumber = batchNumber;
		this.currentBatchCount = currentBatchCount;
		this.count = count;
	}
}
