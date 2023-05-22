package com.anvizent.elt.core.lib.exception;

import java.text.MessageFormat;

import com.anvizent.elt.core.lib.constant.Constants.ExceptionMessage;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class InvalidConfigValueException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidConfigValueException(String key, String value) {
		super(MessageFormat.format(ExceptionMessage.INVALID_CONFIG_VALUE, value, key));
	}

	public InvalidConfigValueException(String key, String value, Throwable throwable) {
		super(MessageFormat.format(ExceptionMessage.INVALID_CONFIG_VALUE, value, key), throwable);
	}
}
