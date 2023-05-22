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
public class AnvizentSQLBatchStatsCalculator extends BaseSQLBatchStatsCalculator<HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public AnvizentSQLBatchStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, Iterator<HashMap<String, Object>> rows, HashMap<String, Object> inputRow,
			Object output, AnvizentFunctionException exception) throws AccumulationException {
		DBWrittenStats sqlWrittenStats = (DBWrittenStats) output;

		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.WRITTEN) && sqlWrittenStats.isWritten()) {
			return sqlWrittenStats.getInsertedRowCount() + sqlWrittenStats.getUpdatedRowCount();
		} else if (statsCategory.equals(StatsCategory.ERROR) && statsName.equals(StatsNames.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
