package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;
import com.anvizent.elt.core.lib.stats.beans.BaseDBRetrievalRow;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
@SuppressWarnings("rawtypes")
public class AnvizentRetrievalStatsCalculator<T, R> extends BaseRetrievalStatsCalculator<T, R> {

	private static final long serialVersionUID = 1L;

	public AnvizentRetrievalStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	protected double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, R outputRow, BaseDBRetrievalRow sqlRetrievalRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OTHER) && statsName.equals(StatsNames.LOOKUP_COUNT) && sqlRetrievalRow.getOutputRowsCount() > 0
				&& !sqlRetrievalRow.isCached()) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OTHER) && statsName.equals(StatsNames.LOOKEDUP_ROWS) && sqlRetrievalRow.getOutputRowsCount() > 0
				&& !sqlRetrievalRow.isCached()) {
			return sqlRetrievalRow.getLookedUpRowsCount();
		} else if (statsCategory.equals(StatsCategory.OTHER) && statsName.equals(StatsNames.CACHE) && sqlRetrievalRow.getOutputRowsCount() > 0
				&& sqlRetrievalRow.isCached()) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.INSERTED) && sqlRetrievalRow.isInserted()) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.OUT) && outputRow != null) {
			return sqlRetrievalRow.getOutputRowsCount();
		} else if (statsCategory.equals(StatsCategory.ERROR) && statsName.equals(StatsNames.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
