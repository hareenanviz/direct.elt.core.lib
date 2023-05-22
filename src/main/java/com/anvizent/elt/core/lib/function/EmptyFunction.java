package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class EmptyFunction extends AnvizentFunction {

	private static final long serialVersionUID = 1L;

	public EmptyFunction(ConfigBean configBean, LinkedHashMap<String, AnvizentDataType> structure, LinkedHashMap<String, AnvizentDataType> newStructure,
			ArrayList<AnvizentAccumulator> accumulators, AnvizentVoidFunction errorHandlerSinkFunction, JobDetails jobDetails)
			throws InvalidArgumentsException {
		super(configBean, null, structure, newStructure, null, accumulators, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	public HashMap<String, Object> process(HashMap<String, Object> row) throws ValidationViolationException, RecordProcessingException {
		return new LinkedHashMap<>(row);
	}
}
