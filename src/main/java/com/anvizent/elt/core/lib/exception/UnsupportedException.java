package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class UnsupportedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnsupportedException() {
		super();
	}

	public UnsupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedException(String message) {
		super(message);
	}

	public UnsupportedException(Throwable cause) {
		super(cause);
	}
}
