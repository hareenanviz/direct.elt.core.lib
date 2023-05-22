package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentFunctionException extends Exception {

	private static final long serialVersionUID = 1L;

	public AnvizentFunctionException() {
		super();
	}

	public AnvizentFunctionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AnvizentFunctionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnvizentFunctionException(String message) {
		super(message);
	}

	public AnvizentFunctionException(Throwable cause) {
		super(cause);
	}

}
