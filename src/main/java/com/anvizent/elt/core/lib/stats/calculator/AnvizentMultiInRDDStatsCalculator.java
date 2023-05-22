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
public class AnvizentMultiInRDDStatsCalculator extends MultiInRDDStatsCalculator {

	private static final long serialVersionUID = 1L;

	public AnvizentMultiInRDDStatsCalculator(StatsCategory statsCategory, String statsName) {
		super(statsCategory, statsName);
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, HashMap<String, JavaRDD<HashMap<String, Object>>> inputRDDs,
			JavaRDD<HashMap<String, Object>> outputRDD, AnvizentFunctionException exception) throws AccumulationException {
		if (statsCategory.equals(StatsCategory.IN) && statsName.equals(StatsNames.IN)) {
			return getInputCount(inputRDDs);
		} else if (statsCategory.equals(StatsCategory.OUT) && statsName.equals(StatsNames.OUT)) {
			return outputRDD.count();
		} else {
			return 0;
		}
	}

	private long getInputCount(HashMap<String, JavaRDD<HashMap<String, Object>>> inputRDDs) {
		long count = 0;

		for (JavaRDD<HashMap<String, Object>> inputRDD : inputRDDs.values()) {
			count = count + inputRDD.count();
		}

		return count;
	}
}
