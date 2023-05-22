package com.anvizent.elt.core.lib.exception;

import com.anvizent.elt.core.lib.exception.RecordProcessingException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class DateParseException extends RecordProcessingException {

	private static final long serialVersionUID = 1L;

	public DateParseException() {
		super();
	}

	public DateParseException(String message) {
		super(message);
	}

	public DateParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DateParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateParseException(Throwable cause) {
		super(cause);
	}

}
