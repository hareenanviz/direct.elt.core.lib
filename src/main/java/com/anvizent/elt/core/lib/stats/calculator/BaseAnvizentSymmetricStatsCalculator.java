package com.anvizent.elt.core.lib.stats.calculator;

import com.anvizent.elt.core.lib.constant.StatsCategory;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentSymmetricStatsCalculator<T, R> extends BaseStatsCalculator<T, R> {

	private static final long serialVersionUID = 1L;

	public BaseAnvizentSymmetricStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}
}
