package com.anvizent.elt.core.lib.exception;

import com.anvizent.elt.core.lib.constant.Constants.ExceptionMessage;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ImproperValidationException extends RecordProcessingException {
	private static final long serialVersionUID = 1L;

	public ImproperValidationException() {
		super(ExceptionMessage.IMPROPER_VALIDATION_FROM_DEV_TEAM);
	}

	public ImproperValidationException(Throwable throwable) {
		super(ExceptionMessage.IMPROPER_VALIDATION_FROM_DEV_TEAM, throwable);
	}
}
