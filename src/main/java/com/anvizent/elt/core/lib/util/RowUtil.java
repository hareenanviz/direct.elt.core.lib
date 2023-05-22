package com.anvizent.elt.core.lib.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.anvizent.elt.commons.constants.Constants.ELTConstants;
import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class RowUtil {

	public static void addEhDataPrefix(HashMap<String, Object> row, HashMap<String, Object> errorRow) {
		if (row != null && !row.isEmpty()) {
			for (String key : row.keySet()) {
				errorRow.put(ELTConstants.ERROR_DATA + key, row.get(key));
			}
		}
	}

	public static void addEhDataPrefix(HashMap<String, Object> row) {
		if (row != null && !row.isEmpty()) {
			HashSet<String> keysBackup = new HashSet<>(row.keySet());
			for (String key : keysBackup) {
				row.put(ELTConstants.ERROR_DATA + key, row.remove(key));
			}
		}
	}

	public static void addErrorDetails(DataCorruptedException exception, HashMap<String, Object> row) {
		row.put(ELTConstants.ERROR_TYPE, exception.getCause().getClass().getName());
		row.put(ELTConstants.ERROR_MESSAGE, exception.getCause().getMessage());
		row.put(ELTConstants.ERROR_FULL_DETAILS, ExceptionUtils.getStackTrace(exception));
	}

	public static void addUserDetails(HashMap<String, Object> row) {
		row.put(ELTConstants.CREATED_BY, ELTConstants.CREATED_BY_DEFAULT);
		row.put(ELTConstants.CREATED_DATE, new Date());
		row.put(ELTConstants.UPDATED_BY, ELTConstants.CREATED_BY_DEFAULT);
		row.put(ELTConstants.UPDATED_DATE, new Date());
	}

	public static void addJobDetails(JobDetails jobDetails, HashMap<String, Object> row) {
		row.put(ELTConstants.JOB_NAME, jobDetails.getJobName());
		row.put(ELTConstants.COMPONENT_NAME, jobDetails.getComponentName());
		row.put(ELTConstants.COMPONENT, jobDetails.getComponentConfigName());
		row.put(ELTConstants.INTERNAL_RDD_NAME, jobDetails.getInternalRDDName());
	}
}
