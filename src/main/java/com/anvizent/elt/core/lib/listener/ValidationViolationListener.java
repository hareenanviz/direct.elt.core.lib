package com.anvizent.elt.core.lib.listener;

import java.util.Iterator;

import com.anvizent.elt.core.lib.exception.ValidationViolationException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public interface ValidationViolationListener<T> {

	public void validationViolated(T row, ValidationViolationException exception) throws ValidationViolationException;

	public void validationViolated(Iterator<T> rows, ValidationViolationException exception) throws ValidationViolationException;
}
