package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.AnvizentErrorSetter;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class AnvizentFlatMapFunction extends BaseAnvizentSymmetricFlatMapFunction<HashMap<String, Object>, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public AnvizentFlatMapFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<HashMap<String, Object>> validationViolationListener,
	        ArrayList<AnvizentAccumulator> accumulators, AnvizentVoidFunction errorHandlerSinkFunction, JobDetails jobDetails)
	        throws InvalidArgumentsException {
		super(configBean, mappingConfigBean, structure, newStructure, validationViolationListener, accumulators, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	protected BaseAnvizentErrorSetter<HashMap<String, Object>, HashMap<String, Object>> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException {
		return AnvizentErrorSetter.getDefaultInstance();
	}

}
