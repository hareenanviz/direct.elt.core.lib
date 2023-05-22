package com.anvizent.elt.core.lib.stats.beans;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.anvizent.elt.core.lib.AnvizentDataType;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
@SuppressWarnings("unused")
public class DBWrittenRow implements WrittenRow {

	private static final long serialVersionUID = 1L;

	private final LinkedHashMap<String, AnvizentDataType> structure;
	private final HashMap<String, Object> inputRow;
	private final HashMap<String, Object> writtenRow;
	private final boolean updated;

	public DBWrittenRow(LinkedHashMap<String, AnvizentDataType> structure, HashMap<String, Object> inputRow, HashMap<String, Object> writtenRow,
			boolean updated) {
		this.structure = structure;
		this.inputRow = inputRow;
		this.writtenRow = writtenRow;
		this.updated = updated;
	}

	public boolean isWritten() {
		return writtenRow != null;
	}

	public boolean isUpdated() {
		return updated;
	}

}
