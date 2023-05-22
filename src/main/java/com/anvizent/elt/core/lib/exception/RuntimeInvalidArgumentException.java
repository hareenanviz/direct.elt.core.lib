package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 *
 */
public class RuntimeInvalidArgumentException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RuntimeInvalidArgumentException() {
		super();
	}

	public RuntimeInvalidArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuntimeInvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeInvalidArgumentException(String message) {
		super(message);
	}

	public RuntimeInvalidArgumentException(Throwable cause) {
		super(cause);
	}
}
