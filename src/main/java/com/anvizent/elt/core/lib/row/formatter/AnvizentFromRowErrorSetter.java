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
public class AnvizentFromRowErrorSetter implements BaseAnvizentErrorSetter<Row, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;
	private static AnvizentFromRowErrorSetter anvizentFromRowErrorSetter;

	public static AnvizentFromRowErrorSetter getDefaultInstance() throws InvalidArgumentsException {
		if (anvizentFromRowErrorSetter == null) {
			synchronized (AnvizentErrorSetter.class) {
				if (anvizentFromRowErrorSetter == null) {
					anvizentFromRowErrorSetter = new AnvizentFromRowErrorSetter();
				}
			}
		}

		return anvizentFromRowErrorSetter;
	}

	private ConvertFromRowFunction convertFromRowFunction = new ConvertFromRowFunction(new ConfigBean(), null, null, null, null);

	public AnvizentFromRowErrorSetter() throws InvalidArgumentsException {
	}

	public HashMap<String, Object> convertRow(JobDetails jobDetails, Row row, DataCorruptedException exception) throws Exception {
		Iterator<HashMap<String, Object>> newRows = convertFromRowFunction.call(row);
		HashMap<String, Object> newRow;

		if (newRows.hasNext()) {
			newRow = newRows.next();
		} else {
			throw new AnvizentErrorSetterException("Unable to convert row: " + row + ", to java.util.LinkedHashMap<String, Object>");
		}

		HashMap<String, Object> errorRow = new HashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(newRow, errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
