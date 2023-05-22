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
public class AnvizentErrorSetter2 implements BaseAnvizentSymetricErrorSetter2<HashMap<String, Object>> {
	private static AnvizentErrorSetter2 anvizentErrorSetter2;

	public static AnvizentErrorSetter2 getDefaultInstance() {
		if (anvizentErrorSetter2 == null) {
			synchronized (AnvizentErrorSetter2.class) {
				if (anvizentErrorSetter2 == null) {
					anvizentErrorSetter2 = new AnvizentErrorSetter2();
				}
			}
		}

		return anvizentErrorSetter2;
	}

	public HashMap<String, Object> convertRow(JobDetails jobDetails, HashMap<String, Object> row_1, HashMap<String, Object> row_2,
			DataCorruptedException exception) {
		HashMap<String, Object> errorRow = new HashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(row_2, errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
