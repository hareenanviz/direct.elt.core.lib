package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;
import com.anvizent.elt.core.lib.stats.beans.DBWrittenRow;
import com.anvizent.elt.core.lib.stats.beans.WrittenRow;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class SQLSinkStatsCalculator extends AnvizentVoidStatsCalculator {

	private static final long serialVersionUID = 1L;

	public SQLSinkStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	protected double getValueToAdd(StatsCategory statsCategory, String statsName, HashMap<String, Object> inputRow, WrittenRow writtenRow,
			AnvizentFunctionException exception) throws AccumulationException {
		DBWrittenRow sqlWrittenRow = (DBWrittenRow) writtenRow;

		if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.INSERTED) && writtenRow.isWritten() && !sqlWrittenRow.isUpdated()) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.UPDATED) && writtenRow.isWritten() && sqlWrittenRow.isUpdated()) {
			return 1;
		} else {
			return super.getValueToAdd(statsCategory, statsName, inputRow, sqlWrittenRow, exception);
		}
	}
}
