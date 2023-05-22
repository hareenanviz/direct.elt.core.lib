package com.anvizent.elt.core.lib.exception;

import com.anvizent.elt.core.lib.exception.RecordProcessingException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class UnsupportedCoerceException extends RecordProcessingException {

	private static final long serialVersionUID = 1L;

	public UnsupportedCoerceException() {
		super();
	}

	public UnsupportedCoerceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportedCoerceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedCoerceException(String message) {
		super(message);
	}

	public UnsupportedCoerceException(Throwable cause) {
		super(cause);
	}

}
