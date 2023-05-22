package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class DataCorruptedException extends AnvizentFunctionException {

	private static final long serialVersionUID = 1L;

	public DataCorruptedException(String message) {
		super(message);
	}

	public DataCorruptedException(String message, Exception exception) {
		super(message, exception);
	}

	public DataCorruptedException(String message, Throwable exception) {
		super(message, exception);
	}

	public DataCorruptedException(Exception exception) {
		super(exception.getMessage(), exception);
	}
}
