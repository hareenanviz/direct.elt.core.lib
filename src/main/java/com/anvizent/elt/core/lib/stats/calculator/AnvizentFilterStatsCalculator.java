package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentFilterStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<HashMap<String, Object>, Boolean> {

	private static final long serialVersionUID = 1L;

	public AnvizentFilterStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, HashMap<String, Object> inputRow, Boolean outputRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OTHER) && statsName.equals(StatsNames.FILTERED) && !outputRow) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.OUT) && outputRow) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.ERROR) && statsName.equals(StatsNames.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
