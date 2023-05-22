package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseMapToPairStatsCalculator<T, K, V> implements IStatsCalculator {

	private static final long serialVersionUID = 1L;

	protected StatsCategory statsCategory;
	protected String statsName;
	protected double defaultValue = 0;

	public BaseMapToPairStatsCalculator(StatsCategory statsCategory, String statsName) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
	}

	public BaseMapToPairStatsCalculator(StatsCategory statsCategory, String statsName, double defaultValue) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.defaultValue = defaultValue;
	}

	public double getValue(StatsCategory statsCategory, String statsName, T inputRow, Tuple2<K, V> outputRow, AnvizentFunctionException exception)
			throws AccumulationException {
		if (this.statsCategory.equals(statsCategory) && this.statsName.equals(statsName)) {
			return getValueToAdd(statsCategory, statsName, inputRow, outputRow, exception);
		} else {
			return defaultValue;
		}
	}

	protected abstract double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, Tuple2<K, V> outputRow,
			AnvizentFunctionException exception) throws AccumulationException;
}
