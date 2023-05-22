package com.anvizent.elt.core.lib.stats.beans;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class DBWrittenStats implements WrittenRow {

	private static final long serialVersionUID = 1L;

	private long insertedRowCount;
	private long updatedRowCount;
	private long errorRowCount;

	public long getInsertedRowCount() {
		return insertedRowCount;
	}

	public void setInsertedRowCount(long insertedRowCount) {
		this.insertedRowCount = insertedRowCount;
	}

	public long getUpdatedRowCount() {
		return updatedRowCount;
	}

	public void setUpdatedRowCount(long updatedRowCount) {
		this.updatedRowCount = updatedRowCount;
	}

	public long getErrorRowCount() {
		return errorRowCount;
	}

	public void setErrorRowCount(long errorRowCount) {
		this.errorRowCount = errorRowCount;
	}

	public boolean isWritten() {
		return insertedRowCount > 0 || updatedRowCount > 0;
	}

	public boolean isUpdated() {
		return updatedRowCount > 0;
	}

	public boolean isInserted() {
		return insertedRowCount > 0;
	}

	public boolean hasError() {
		return errorRowCount > 0;
	}

	public DBWrittenStats(long insertedRowCount, long updatedRowCount) {
		this.insertedRowCount = insertedRowCount;
		this.updatedRowCount = updatedRowCount;
	}

	public DBWrittenStats(long insertedRowCount, long updatedRowCount, long errorRowCount) {
		this.insertedRowCount = insertedRowCount;
		this.updatedRowCount = updatedRowCount;
		this.errorRowCount = errorRowCount;
	}
}
