package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.spark.sql.Row;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.InvalidRelationException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.AnvizentFromRowErrorSetter;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class AnvizentRowVoidFunction extends BaseAnvizentVoidFunction<Row, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public AnvizentRowVoidFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
			LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<Row> validationViolationListener,
			ArrayList<AnvizentAccumulator> accumulators, AnvizentVoidFunction errorHandlerSinkFunction, JobDetails jobDetails)
			throws InvalidRelationException, InvalidArgumentsException {
		super(configBean, mappingConfigBean, structure, newStructure, validationViolationListener, accumulators, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	protected BaseAnvizentErrorSetter<Row, HashMap<String, Object>> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException {
		return AnvizentFromRowErrorSetter.getDefaultInstance();
	}

}
