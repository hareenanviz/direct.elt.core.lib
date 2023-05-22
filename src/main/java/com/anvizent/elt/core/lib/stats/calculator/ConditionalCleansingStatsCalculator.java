package com.anvizent.elt.core.lib.stats.calculator;

import java.util.HashMap;

import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.exception.AccumulationException;
import com.anvizent.elt.core.lib.exception.AnvizentFunctionException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ConditionalCleansingStatsCalculator extends BaseAnvizentSymmetricStatsCalculator<HashMap<String, Object>, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;
	private String fieldName;

	public ConditionalCleansingStatsCalculator(StatsCategory statsCategory, String statsName, String fieldName) {
		super(statsCategory, statsName);
		this.fieldName = fieldName;
	}

	@Override
	protected double getValueToAdd(StatsCategory statsCategory, String statsName, HashMap<String, Object> inputRow, HashMap<String, Object> outputRow,
			AnvizentFunctionException exception) throws AccumulationException {
		if ((inputRow.get(fieldName) == null && outputRow.get(fieldName) != null) || (inputRow.get(fieldName) != null && outputRow.get(fieldName) == null)) {
			return 1;
		} else if (inputRow.get(fieldName) != null && outputRow.get(fieldName) != null && !inputRow.get(fieldName).equals(outputRow.get(fieldName))) {
			return 1;
		} else {
			return 0;
		}
	}
}
