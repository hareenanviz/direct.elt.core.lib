package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import org.apache.spark.api.java.JavaRDD;

import com.anvizent.elt.core.lib.constant.Constants.StatsNames;
import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentRDDStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<JavaRDD<HashMap<String, Object>>, JavaRDD<HashMap<String, Object>>> {

	private static final long serialVersionUID = 1L;

	public AnvizentRDDStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, JavaRDD<HashMap<String, Object>> inputRDD,
			JavaRDD<HashMap<String, Object>> outputRDD, AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return inputRDD.count();
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.OUT)) {
			return outputRDD.count();
		} else {
			return 0;
		}
	}
}