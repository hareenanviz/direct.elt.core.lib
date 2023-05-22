package com.anvizent.elt.core.lib.row.formatter;

import java.util.LinkedHashMap;

import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;
import com.anvizent.elt.core.lib.util.RowUtil;

import scala.Tuple2;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentPairToNormalErrorSetter
		implements BaseAnvizentPairToNormalErrorSetter<LinkedHashMap<String, Object>, LinkedHashMap<String, Object>, LinkedHashMap<String, Object>> {

	private static final long serialVersionUID = 1L;
	private static AnvizentPairToNormalErrorSetter anvizentPairToNormalErrorSetter;

	public static AnvizentPairToNormalErrorSetter getDefaultInstance() {
		if (anvizentPairToNormalErrorSetter == null) {
			synchronized (AnvizentErrorSetter.class) {
				if (anvizentPairToNormalErrorSetter == null) {
					anvizentPairToNormalErrorSetter = new AnvizentPairToNormalErrorSetter();
				}
			}
		}

		return anvizentPairToNormalErrorSetter;
	}

	public LinkedHashMap<String, Object> convertRow(JobDetails jobDetails, Tuple2<LinkedHashMap<String, Object>, LinkedHashMap<String, Object>> row,
			DataCorruptedException exception) {
		LinkedHashMap<String, Object> errorRow = new LinkedHashMap<>();

		RowUtil.addJobDetails(jobDetails, errorRow);
		RowUtil.addEhDataPrefix(row._2(), errorRow);
		RowUtil.addErrorDetails(exception, errorRow);
		RowUtil.addUserDetails(errorRow);

		return errorRow;
	}

}
