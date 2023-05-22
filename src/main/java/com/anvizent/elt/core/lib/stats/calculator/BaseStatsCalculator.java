package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseStatsCalculator<T, R> implements IStatsCalculator {

	private static final long serialVersionUID = 1L;

	protected StatsCategory statsCategory;
	protected String statsName;
	protected double defaultValue = 0;

	public BaseStatsCalculator(StatsCategory statsCategory, String statsName) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
	}

	public BaseStatsCalculator(StatsCategory statsCategory, String statsName, double defaultValue) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.defaultValue = defaultValue;
	}

	public double getValue(StatsCategory statsCategory, String statsName, T inputRow, R outputRow, AnvizentFunctionException exception)
			throws AccumulationException {
		if (this.statsCategory.equals(statsCategory) && this.statsName.equals(statsName)) {
			return getValueToAdd(statsCategory, statsName, inputRow, outputRow, exception);
		} else {
			return defaultValue;
		}
	}

	protected abstract double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, R outputRow, AnvizentFunctionException exception)
			throws AccumulationException;

}
