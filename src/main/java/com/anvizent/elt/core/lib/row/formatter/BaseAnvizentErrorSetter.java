package com.anvizent.elt.core.lib.row.formatter;

import java.io.Serializable;

import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public interface BaseAnvizentErrorSetter<T, R> extends Serializable {

	R convertRow(JobDetails jobDetails, T row, DataCorruptedException exception) throws Exception;

}
