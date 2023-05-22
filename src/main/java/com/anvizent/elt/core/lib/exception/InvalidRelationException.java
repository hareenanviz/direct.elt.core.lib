package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class InvalidRelationException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRelationException() {
	}

	public InvalidRelationException(String message) {
		super(message);
	}

	public InvalidRelationException(Throwable cause) {
		super(cause);
	}

	public InvalidRelationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRelationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
