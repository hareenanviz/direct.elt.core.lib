package com.anvizent.elt.core.lib.stats.beans;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class DBRetrievalRow<T, R> extends BaseDBRetrievalRow<T, R> {

	private static final long serialVersionUID = 1L;

	private final R outputRow;

	public DBRetrievalRow(T inputRow, R outputRow, boolean inserted, boolean cached, Integer outputRowsCount, Integer lookedUpRowsCount) {
		super(inputRow, inserted, cached, outputRowsCount, lookedUpRowsCount);
		this.outputRow = outputRow;
	}

	public R getOutputRow() {
		return outputRow;
	}
}
