package com.anvizent.elt.core.lib.function;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.spark.sql.Row;

import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.config.bean.MappingConfigBean;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.listener.ValidationViolationListener;
import com.anvizent.elt.core.lib.row.formatter.AnvizentFromRowErrorSetter;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class AnvizentRowFilterFunction extends BaseAnvizentFilterFunction<Row, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public AnvizentRowFilterFunction(ConfigBean configBean, MappingConfigBean mappingConfigBean, LinkedHashMap<String, AnvizentDataType> structure,
			LinkedHashMap<String, AnvizentDataType> newStructure, ValidationViolationListener<Row> validationViolationListener,
			AnvizentVoidFunction errorHandlerSinkFunction, JobDetails jobDetails) throws InvalidArgumentsException {
		super(configBean, mappingConfigBean, structure, newStructure, validationViolationListener, null, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	protected BaseAnvizentErrorSetter<Row, HashMap<String, Object>> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException {
		return AnvizentFromRowErrorSetter.getDefaultInstance();
	}
}
