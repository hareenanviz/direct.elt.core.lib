package com.anvizent.elt.core.lib.row.formatter;

import java.util.HashMap;

import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.util.RowUtil;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentErrorSetter implements BaseAnvizentSymetricErrorSetter<HashMap<String, Object>> {
	private static final long serialVersionUID = 1L;
	private static AnvizentErrorSetter anvizentErrorSetter;

	public static AnvizentErrorSetter getDefaultInstance() {
		if (anvizentErrorSetter == null) {
			synchronized (AnvizentErrorSetter.class) {
				if (anvizentErrorSetter == null) {
					anvizentErrorSetter = new AnvizentErrorSetter();
				}
			}
		}

		return anvizentErrorSetter;
	}

	public HashMap<String, Object> convertRow(JobDetails jobDetails, HashMap<String, Object> row, DataCorruptedException exception) {
		HashMap<String, Object> errorRow = new HashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(row, errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
