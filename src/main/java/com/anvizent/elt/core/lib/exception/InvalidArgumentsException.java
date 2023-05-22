package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class InvalidArgumentsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidArgumentsException() {
		super();
	}

	public InvalidArgumentsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidArgumentsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArgumentsException(String message) {
		super(message);
	}

	public InvalidArgumentsException(Throwable cause) {
		super(cause);
	}
}
