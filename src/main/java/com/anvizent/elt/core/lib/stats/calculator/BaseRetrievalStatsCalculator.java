package com.anvizent.elt.core.lib.stats.calculator;

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
public abstract class BaseRetrievalStatsCalculator<T, R> implements IStatsCalculator {

	private static final long serialVersionUID = 1L;

	protected StatsCategory statsCategory;
	protected String statsName;
	protected double defaultValue = 0;

	public BaseRetrievalStatsCalculator(StatsCategory statsCategory, String statsName) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
	}

	public BaseRetrievalStatsCalculator(StatsCategory statsCategory, String statsName, double defaultValue) {
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.defaultValue = defaultValue;
	}

	public double getValue(StatsCategory statsCategory, String statsName, T inputRow, R outputRow, BaseDBRetrievalRow sqlRetrievalRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if (this.statsCategory.equals(statsCategory) && this.statsName.equals(statsName)) {
			return getValueToAdd(statsCategory, statsName, inputRow, outputRow, sqlRetrievalRow, exception);
		} else {
			return defaultValue;
		}
	}

	protected abstract double getValueToAdd(StatsCategory statsCategory, String statsName, T inputRow, R outputRow, BaseDBRetrievalRow sqlRetrievalRow,
			AnvizentFunctionException exception) throws AccumulationException;
}
