package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import org.apache.spark.sql.Row;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentToRowStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<HashMap<String, Object>, Row> {

	private static final long serialVersionUID = 1L;

	public AnvizentToRowStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, HashMap<String, Object> inputRow, Row outputRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && (statsName.equals(StatsNames.IN) || statsName.equals(StatsNames.READ))) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && (statsName.equals(StatsNames.OUT) || statsName.equals(StatsNames.WRITTEN)) && outputRow != null) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
