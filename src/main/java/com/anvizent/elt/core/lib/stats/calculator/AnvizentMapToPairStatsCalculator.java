package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentMapToPairStatsCalculator<T, K> extends BaseMapToPairStatsCalculator<T, K, T> {

	private static final long serialVersionUID = 1L;

	public AnvizentMapToPairStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, Tuple2<K, T> outputRow, AnvizentFunctionException exception)
			throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.OUT) && outputRow != null) {
			return 1;
		} else if (statsCategory.equals(StatsCategory.ERROR) && statsName.equals(StatsNames.ERROR) && exception != null) {
			return 1;
		} else {
			return 0;
		}
	}
}
