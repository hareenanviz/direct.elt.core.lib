package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import org.apache.spark.api.java.JavaRDD;

import com.anvizent.elt.core.lib.constant.StatsCategory;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class RDDStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<JavaRDD<HashMap<String, Object>>, JavaRDD<HashMap<String, Object>>> {

	private static final long serialVersionUID = 1L;

	public RDDStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}
}
