package com.anvizent.elt.core.lib.stats.beans;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AlwaysWrittenRow implements WrittenRow {

	private static final long serialVersionUID = 1L;

	public boolean isWritten() {
		return true;
	}
}
