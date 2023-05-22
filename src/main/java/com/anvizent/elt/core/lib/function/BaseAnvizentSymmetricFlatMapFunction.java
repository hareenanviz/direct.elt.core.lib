package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;

/**
 * @author Hareen Bejjanki
 * 
 */
public abstract class BaseAnvizentSymmetricFlatMapFunction<R, E> extends BaseAnvizentFlatMapFunction<R, R, E> {

	private static final long serialVersionUID = 1L;

	public BaseAnvizentSymmetricFlatMapFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
	        LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<R> validationViolationListener,
	        ArrayList<AnvizentAccumulator> accumulators, BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction, JobDetails jobDetails)
	        throws InvalidArgumentsException {
		super(configBean, mappingConfigBean, structure, newStructure, validationViolationListener, accumulators, errorHandlerSinkFunction, jobDetails);
	}

}
