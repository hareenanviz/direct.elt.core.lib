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
 * @author Apurva Deshmukh
 *
 */
public abstract class BaseAnvizentSymmetricFunction2<T, E> extends BaseAnvizentFunction2<T, T, T, E> {

	private static final long serialVersionUID = 1L;

	public BaseAnvizentSymmetricFunction2(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
			LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<T> validationViolationListener,
			ArrayList<AnvizentAccumulator> accumulators, BaseAnvizentVoidFunction<E, E> errorHandlerSinkFunction, JobDetails jobDetails)
			throws InvalidArgumentsException {
		super(configBean, mappingConfigBean, structure, newStructure, validationViolationListener, accumulators, errorHandlerSinkFunction, jobDetails);
	}
}
