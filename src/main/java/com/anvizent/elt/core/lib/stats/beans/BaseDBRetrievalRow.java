package com.anvizent.elt.core.lib.stats.beans;

import java.io.Serializable;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseDBRetrievalRow<I, O> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final I inputRow;
	private final boolean inserted;
	private final boolean cached;
	private final Integer outputRowsCount;
	private final Integer lookedUpRowsCount;

	public BaseDBRetrievalRow(I inputRow, boolean inserted, boolean cached, Integer outputRowsCount, Integer lookedUpRowsCount) {
		this.inputRow = inputRow;
		this.inserted = inserted;
		this.cached = cached;
		this.outputRowsCount = outputRowsCount;
		this.lookedUpRowsCount = lookedUpRowsCount;
	}

	public I getInputRow() {
		return inputRow;
	}

	public boolean isInserted() {
		return inserted;
	}

	public boolean isCached() {
		return cached;
	}

	public Integer getOutputRowsCount() {
		return outputRowsCount;
	}

	public Integer getLookedUpRowsCount() {
		return lookedUpRowsCount;
	}

	public abstract O getOutputRow();
}
