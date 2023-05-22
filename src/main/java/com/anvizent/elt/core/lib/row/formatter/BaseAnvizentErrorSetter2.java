package com.anvizent.elt.core.lib.row.formatter;

import com.anvizent.elt.core.lib.config.bean.JobDetails;
import com.anvizent.elt.core.lib.exception.DataCorruptedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public interface BaseAnvizentErrorSetter2<T, I, R> {

	R convertRow(JobDetails jobDetails, T row1, I row2, DataCorruptedException exception) throws Exception;

}
