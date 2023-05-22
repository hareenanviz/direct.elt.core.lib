package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizent2StatsCalculator<T, K, V> implements IStatsCalculator {

	private static final long serialVersionUID = 1L;

	protected StatsCategory statsCategory;
	protected String statsName;
	protected double defaultValue = 0;

	public BaseAnvizent2StatsCalculator(StatsCategory statsCategory, String statsName) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
	}

	public BaseAnvizent2StatsCalculator(StatsCategory statsCategory, String statsName, double defaultValue) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.defaultValue = defaultValue;
	}

	public double getValue(StatsCategory statsCategory, String statsName, T inputRow, V outputRow, AnvizentFunctionException exception)
			throws AccumulationException {
		if (this.statsCategory.equals(statsCategory) && this.statsName.equals(statsName)) {
			return getValueToAdd(statsCategory, statsName, inputRow, outputRow, exception);
		} else {
			return defaultValue;
		}
	}

	protected abstract double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, V outputRow, AnvizentFunctionException exception)
			throws AccumulationException;
}
