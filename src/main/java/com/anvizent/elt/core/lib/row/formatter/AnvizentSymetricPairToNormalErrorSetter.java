package com.anvizent.elt.core.lib.row.formatter;

import java.util.HashMap;

import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.util.RowUtil;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentSymetricPairToNormalErrorSetter<K> implements BaseAnvizentPairToNormalErrorSetter<K, HashMap<String, Object>, HashMap<String, Object>> {

	private static final long serialVersionUID = 1L;

	public HashMap<String, Object> convertRow(JobDetails jobDetails, Tuple2<K, HashMap<String, Object>> row, DataCorruptedException exception) {
		HashMap<String, Object> errorRow = new HashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(row._2(), errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
