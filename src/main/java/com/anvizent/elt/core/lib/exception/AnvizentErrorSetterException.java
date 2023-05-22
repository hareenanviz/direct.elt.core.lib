package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentErrorSetterException extends Exception {
	private static final long serialVersionUID = 1L;

	public AnvizentErrorSetterException() {
		super();
	}

	public AnvizentErrorSetterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AnvizentErrorSetterException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnvizentErrorSetterException(String message) {
		super(message);
	}

	public AnvizentErrorSetterException(Throwable cause) {
		super(cause);
	}
}
