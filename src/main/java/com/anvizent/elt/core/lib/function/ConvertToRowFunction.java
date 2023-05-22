package com.anvizent.elt.core.lib.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

import com.anvizent.elt.core.lib.AnvizentAccumulator;
import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.exception.RecordProcessingException;
import com.anvizent.elt.core.lib.exception.ValidationViolationException;
import com.anvizent.elt.core.lib.row.formatter.AnvizentErrorSetter;
import com.anvizent.elt.core.lib.row.formatter.BaseAnvizentErrorSetter;
import com.anvizent.elt.core.lib.util.TypeConversionUtil;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ConvertToRowFunction extends BaseAnvizentFunction<HashMap<String, Object>, Row, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public ConvertToRowFunction(ConfigBean configBean, LinkedHashMap<String, AnvizentDataType> structure, ArrayList<AnvizentAccumulator> anvizentAccumulators,
	        AnvizentVoidFunction errorHandlerSinkFunction, JobDetails jobDetails) throws InvalidArgumentsException {
		super(configBean, null, structure, structure, null, anvizentAccumulators, errorHandlerSinkFunction, jobDetails);
	}

	@Override
	public Row process(HashMap<String, Object> values) throws ValidationViolationException, RecordProcessingException {
		Object[] row = new Object[newStructure.size()];

		int i = 0;
		for (Entry<String, AnvizentDataType> map : newStructure.entrySet()) {
			String key = map.getKey();
			Object value = values.get(key);
			if (configBean.getName().equalsIgnoreCase("console-3")) {
				System.out.println("key: " + key + ", value: " + value + ", type: " + value.getClass());
			}
			row[i] = TypeConversionUtil.convertToSqlTypes(value);
			++i;
		}

		return RowFactory.create(row);
	}

	@Override
	protected BaseAnvizentErrorSetter<HashMap<String, Object>, HashMap<String, Object>> getBaseAnvizentErrorRowSetter() throws InvalidArgumentsException {
		return AnvizentErrorSetter.getDefaultInstance();
	}

}
