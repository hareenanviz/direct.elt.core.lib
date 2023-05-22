package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;
import java.util.Iterator;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;
import com.anvizent.elt.core.lib.stats.beans.DBWrittenStats;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class SQLBatchSinkStatsCalculator extends AnvizentSQLBatchStatsCalculator {

	private static final long serialVersionUID = 1L;

	public SQLBatchSinkStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, Iterator<HashMap<String, Object>> rows, HashMap<String, Object> inputRow,
			Object output, AnvizentFunctionException exception) throws AccumulationException {
		DBWrittenStats sqlWrittenStats = (DBWrittenStats) output;

		if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.INSERTED) && sqlWrittenStats.isWritten() && sqlWrittenStats.isInserted()) {
			return sqlWrittenStats.getInsertedRowCount();
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.UPDATED) && sqlWrittenStats.isWritten()
				&& sqlWrittenStats.isUpdated()) {
			return sqlWrittenStats.getUpdatedRowCount();
		} else {
			return super.getValueToAdd(statsCategory, statsName, rows, inputRow, sqlWrittenStats, exception);
		}
	}
}
