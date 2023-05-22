package com.anvizent.elt.core.lib.row.formatter;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.spark.sql.Row;

import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.AnvizentErrorSetterException;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.exception.InvalidArgumentsException;
import com.anvizent.elt.core.lib.function.ConvertFromRowFunction;
import com.anvizent.elt.core.lib.util.RowUtil;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentFromRowErrorSetter2 implements BaseAnvizentErrorSetter2<Row, Row, HashMap<String, Object>> {
	private static AnvizentFromRowErrorSetter2 anvizentFromRowErrorSetter2;

	public static AnvizentFromRowErrorSetter2 getDefaultInstance() throws InvalidArgumentsException {
		if (anvizentFromRowErrorSetter2 == null) {
			synchronized (AnvizentErrorSetter.class) {
				if (anvizentFromRowErrorSetter2 == null) {
					anvizentFromRowErrorSetter2 = new AnvizentFromRowErrorSetter2();
				}
			}
		}

		return anvizentFromRowErrorSetter2;
	}

	private ConvertFromRowFunction convertFromRowFunction = new ConvertFromRowFunction(new ConfigBean(), null, null, null, null);

	public AnvizentFromRowErrorSetter2() throws InvalidArgumentsException {
	}

	public HashMap<String, Object> convertRow(JobDetails jobDetails, Row row_1, Row row_2, DataCorruptedException exception) throws Exception {
		Iterator<HashMap<String, Object>> newRows = convertFromRowFunction.call(row_2);
		HashMap<String, Object> newRow;

		if (newRows.hasNext()) {
			newRow = newRows.next();
		} else {
			throw new AnvizentErrorSetterException("Unable to conver row: " + row_2 + ", to java.util.LinkedHashMap<String, Object>");
		}

		HashMap<String, Object> errorRow = new HashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(newRow, errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
