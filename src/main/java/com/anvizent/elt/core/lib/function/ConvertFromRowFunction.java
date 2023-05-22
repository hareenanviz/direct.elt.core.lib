package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.spark.sql.Row;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;
import com.anvizent.elt.core.lib.row.formatter.AnvizentFromRowErrorSetter;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;
import com.anvizent.elt.core.lib.util.TypeConversionUtil;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ConvertFromRowFunction extends BaseAnvizentFunction<Row, HashMap<String, Object>, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public ConvertFromRowFunction(ConfigBean configBean, LinkedHashMap<String, AnvizentDataType> structure, ArrayList<AnvizentAccumulator> anvizentAccumulators,
			BaseAnvizentVoidFunction<HashMap<String, Object>, HashMap<String, Object>> errorHandlerSinkFunction, JobDetails jobDetails)
			throws InvalidArgumentsException {
		super(configBean, null, structure, structure, null, anvizentAccumulators, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	public HashMap<String, Object> process(Row row) throws ValidationViolationException, RecordProcessingException {
		String[] fields = row.schema().fieldNames();
		HashMap<String, Object> newRow = new HashMap<String, Object>();

		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = row.get(row.fieldIndex(fields[i]));
			newRow.put(fields[i], TypeConversionUtil.convertToGeneralType(fieldValue));
		}

		return newRow;
	}

	@Override
	protected BaseAnvizentErrorSetter<Row, HashMap<String, Object>> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException {
		return AnvizentFromRowErrorSetter.getDefaultInstance();
	}

}
