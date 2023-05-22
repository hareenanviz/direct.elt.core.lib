package com.anvizent.elt.core.lib.exception;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class RecordProcessingException extends AnvizentFunctionException {

	private static final long serialVersionUID = 1L;

	public RecordProcessingException() {
		super();
	}

	public RecordProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecordProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordProcessingException(String message) {
		super(message);
	}

	public RecordProcessingException(Throwable cause) {
		super(cause);
	}

}
