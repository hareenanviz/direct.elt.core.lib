package com.anvizent.elt.core.lib.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.spark.sql.Row;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ValidationViolationException extends AnvizentFunctionException {

	private static final long serialVersionUID = 1L;

	public ValidationViolationException() {
		super();
	}

	public ValidationViolationException(LinkedHashMap<String, Object> row, LinkedHashMap<String, Object> violatedValues, String message,
			ArrayList<String> keyFields) {
		// TODO
	}

	public ValidationViolationException(Row row, LinkedHashMap<String, Object> violatedValues, String message, ArrayList<String> keyFields) {
		// TODO
	}

	public ValidationViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidationViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationViolationException(String message) {
		super(message);
	}

	public ValidationViolationException(Throwable cause) {
		super(cause);
	}
}
