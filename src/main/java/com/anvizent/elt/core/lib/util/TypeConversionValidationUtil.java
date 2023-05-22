package com.anvizent.elt.core.lib.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hareen Bejjanki
 *
 */
@SuppressWarnings("rawtypes")
public class TypeConversionValidationUtil {

	public static boolean canConvertToDataType(Class fieldType, Class coerceToType) {
		if (fieldType.equals(Byte.class)) {
			return canConvertFromByteToOtherType(coerceToType);
		} else if (fieldType.equals(Short.class)) {
			return canConvertFromShortToOtherType(coerceToType);
		} else if (fieldType.equals(Character.class)) {
			return canConvertFromCharToOtherType(coerceToType);
		} else if (fieldType.equals(Integer.class)) {
			return canConvertFromIntToOtherType(coerceToType);
		} else if (fieldType.equals(Long.class)) {
			return canConvertFromLongToOtherType(coerceToType);
		} else if (fieldType.equals(Float.class)) {
			return canConvertFromFloatToOtherType(coerceToType);
		} else if (fieldType.equals(Double.class)) {
			return canConvertFromDoubleToOtherType(coerceToType);
		} else if (fieldType.equals(String.class)) {
			return canConvertFromStringToOtherType(coerceToType);
		} else if (fieldType.equals(Boolean.class)) {
			return canConvertFromBooleanToOther(coerceToType);
		} else if (fieldType.equals(Date.class)) {
			return canConvertFromDateToOther(coerceToType);
		} else if (fieldType.equals(BigDecimal.class)) {
			return canConvertFromBigDecimalToOther(coerceToType);
		} else {
			return false;
		}
	}

	private static boolean canConvertFromBigDecimalToOther(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Integer.class) || coerceToType.equals(Long.class)
		        || coerceToType.equals(Float.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class);
	}

	public static boolean canConvertFromByteToOtherType(Class coerceToType) {
		return coerceToType.equals(Short.class) || coerceToType.equals(Integer.class) || coerceToType.equals(Long.class) || coerceToType.equals(Float.class)
		        || coerceToType.equals(Double.class) || coerceToType.equals(String.class) || coerceToType.equals(Character.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Byte.class);
	}

	public static boolean canConvertFromStringToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Character.class) || coerceToType.equals(Integer.class)
		        || coerceToType.equals(Long.class) || coerceToType.equals(Float.class) || coerceToType.equals(Double.class)
		        || coerceToType.equals(Boolean.class) || coerceToType.equals(String.class) || coerceToType.equals(Date.class)
		        || coerceToType.equals(BigDecimal.class);
	}

	public static boolean canConvertFromDoubleToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Character.class) || coerceToType.equals(Integer.class)
		        || coerceToType.equals(Long.class) || coerceToType.equals(Float.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Double.class);
	}

	public static boolean canConvertFromFloatToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Character.class) || coerceToType.equals(Integer.class)
		        || coerceToType.equals(Long.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Float.class);
	}

	public static boolean canConvertFromLongToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Character.class) || coerceToType.equals(Integer.class)
		        || coerceToType.equals(Float.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class) || coerceToType.equals(Date.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Long.class);
	}

	public static boolean canConvertFromCharToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Short.class) || coerceToType.equals(Integer.class) || coerceToType.equals(Long.class)
		        || coerceToType.equals(Float.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Character.class);
	}

	public static boolean canConvertFromShortToOtherType(Class coerceToType) {
		return coerceToType.equals(Byte.class) || coerceToType.equals(Character.class) || coerceToType.equals(Integer.class) || coerceToType.equals(Long.class)
		        || coerceToType.equals(Float.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Short.class);
	}

	public static boolean canConvertFromIntToOtherType(Class coerceToType) {
		return coerceToType.equals(Short.class) || coerceToType.equals(Character.class) || coerceToType.equals(Byte.class) || coerceToType.equals(Long.class)
		        || coerceToType.equals(Float.class) || coerceToType.equals(Double.class) || coerceToType.equals(String.class)
		        || coerceToType.equals(BigDecimal.class) || coerceToType.equals(Integer.class);
	}

	public static boolean canConvertFromBooleanToOther(Class coerceToType) {
		return coerceToType.equals(String.class) || coerceToType.equals(Boolean.class);
	}

	public static boolean canConvertFromDateToOther(Class coerceToType) {
		return coerceToType.equals(Long.class) || coerceToType.equals(String.class) || coerceToType.equals(Date.class);
	}
}
