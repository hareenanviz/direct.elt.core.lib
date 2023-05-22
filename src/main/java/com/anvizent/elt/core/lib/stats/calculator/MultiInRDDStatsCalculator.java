package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import org.apache.spark.api.java.JavaRDD;

import com.anvizent.elt.core.lib.constant.StatsCategory;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class MultiInRDDStatsCalculator
		extends BaseAnvizentSymmetricStatsCalculator<HashMap<String, JavaRDD<HashMap<String, Object>>>, JavaRDD<HashMap<String, Object>>> {

	private static final long serialVersionUID = 1L;

	public MultiInRDDStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}
}
