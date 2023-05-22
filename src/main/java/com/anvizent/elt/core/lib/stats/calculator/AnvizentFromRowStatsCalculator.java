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
public class AnvizentFromRowStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<Row, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public AnvizentFromRowStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	protected double getValueToAdd(StatsCategory statsCategory, String statsName, Row inputRow, HashMap<String, Object> outputRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && (statsName.equals(StatsNames.IN) || statsName.equals(StatsNames.READ))) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && (statsName.equals(StatsNames.OUT) || statsName.equals(StatsNames.WRITTEN)) && outputRow != null) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && (statsName.equals(StatsNames.OUT) || statsName.equals(StatsNames.WRITTEN)) && exception != null) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.ERROR) && statsName.equals(StatsNames.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
