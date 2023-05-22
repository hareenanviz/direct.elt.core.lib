package com.anvizent.elt.core.lib.exception;

import com.anvizent.elt.core.lib.exception.RecordProcessingException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class CoerceException extends RecordProcessingException {

	private static final long serialVersionUID = 1L;

	public CoerceException() {
		super();
	}

	public CoerceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CoerceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoerceException(String message) {
		super(message);
	}

	public CoerceException(Throwable cause) {
		super(cause);
	}

}
