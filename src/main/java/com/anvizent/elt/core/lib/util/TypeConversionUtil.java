package com.anvizent.elt.core.lib.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import com.anvizent.elt.core.lib.config.bean.ConfigBean;
import com.anvizent.elt.core.lib.constant.Constants.ExceptionMessage;
import com.anvizent.elt.core.lib.exception.DateParseException;
import com.anvizent.elt.core.lib.exception.ImproperValidationException;
import com.anvizent.elt.core.lib.exception.InvalidConfigValueException;
import com.anvizent.elt.core.lib.exception.InvalidSituationException;
import com.anvizent.elt.core.lib.exception.UnsupportedCoerceException;

/**
 * @author Hareen Bejjanki
 *
 */
@SuppressWarnings("rawtypes")
public class TypeConversionUtil {

	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
	private static final char EXPONENT_NOTATION = 'E';

	public static Object dateTypeConversion(Object fieldValue, Class fieldType, Class coerceToType, String coerceToFormat, String configKey)
	        throws DateParseException, UnsupportedCoerceException, InvalidSituationException, ImproperValidationException, InvalidConfigValueException {
		if (fieldType.equals(String.class)) {
			return stringToDateConversion((String) fieldValue, coerceToType, coerceToFormat, configKey);
		} else if (fieldType.equals(Date.class)) {
			return dateToOtherConversion((Date) fieldValue, coerceToType, coerceToFormat);
		} else if (fieldType.equals(Long.class)) {
			return longToDateConversion((Long) fieldValue, coerceToType);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object dataTypeConversion(Object fieldValue, Class fieldType, Class coerceToType, String coerceToFormat, Integer scale,
	        String dateFormatConfigKey, ZoneOffset timeZoneOffset)
	        throws UnsupportedCoerceException, InvalidSituationException, DateParseException, ImproperValidationException, InvalidConfigValueException {
		try {
			if (fieldType.equals(Byte.class)) {
				return byteToOtherTypeConversion((Byte) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Short.class)) {
				return shortToOtherTypeConversion((Short) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Character.class)) {
				return charToOtherTypeConversion((Character) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Integer.class)) {
				return intToOtherTypeConversion((Integer) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Long.class)) {
				return longToOtherTypeConversion((Long) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Float.class)) {
				return floatToOtherTypeConversion((Float) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(Double.class)) {
				return doubleToOtherTypeConversion((Double) fieldValue, coerceToType, scale);
			} else if (fieldType.equals(String.class)) {
				return stringToOtherTypeConversion((String) fieldValue, coerceToType, coerceToFormat, scale, dateFormatConfigKey);
			} else if (fieldType.equals(Boolean.class)) {
				return booleanToOtherConversion((Boolean) fieldValue, coerceToType);
			} else if (fieldType.equals(Date.class)) {
				return dateToOtherConversion((Date) fieldValue, coerceToType, coerceToFormat);
			} else if (fieldType.equals(OffsetDateTime.class)) {
				return offsetDateTypeToDateConversion(fieldValue, fieldType, coerceToType, timeZoneOffset);
			} else if (fieldType.equals(BigDecimal.class)) {
				return bigDecimalToOtherConversion((BigDecimal) fieldValue, coerceToType);
			} else {
				throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
			}
		} catch (NumberFormatException exception) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION, exception);
		}
	}

	private static Object bigDecimalToOtherConversion(BigDecimal fieldValue, Class coerceToType) throws InvalidSituationException, UnsupportedCoerceException {
		if (fieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return fieldValue.byteValue();
		} else if (coerceToType.equals(Short.class)) {
			return fieldValue.shortValue();
		} else if (coerceToType.equals(Integer.class)) {
			return fieldValue.intValue();
		} else if (coerceToType.equals(Long.class)) {
			return fieldValue.longValue();
		} else if (coerceToType.equals(Float.class)) {
			return fieldValue.floatValue();
		} else if (coerceToType.equals(Double.class)) {
			return fieldValue.doubleValue();
		} else if (coerceToType.equals(String.class)) {
			return DECIMAL_FORMAT.format(fieldValue.doubleValue());
		} else if (coerceToType.equals(BigDecimal.class)) {
			return fieldValue;
		} else if (coerceToType.equals(Character.class) || coerceToType.equals(Boolean.class) || coerceToType.equals(Date.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object byteToOtherTypeConversion(Byte fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fromFieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fromFieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fromFieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fromFieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			return Byte.toString(fromFieldValue);
		} else if (coerceToType.equals(Character.class)) {
			byte fieldValue = fromFieldValue;
			return (char) fieldValue;
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Byte.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object stringToOtherTypeConversion(String fromFieldValue, Class coerceToType, String coerceToFormat, Integer scale,
	        String dateFormatConfigKey)
	        throws UnsupportedCoerceException, InvalidSituationException, DateParseException, ImproperValidationException, InvalidConfigValueException {
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return Byte.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Short.class)) {
			return Short.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Character.class)) {
			if (fromFieldValue.length() == 1) {
				return fromFieldValue.charAt(0);
			} else {
				throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
			}
		} else if (coerceToType.equals(Integer.class)) {
			return Integer.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Long.class)) {
			return Long.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Float.class)) {
			return Float.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Double.class)) {
			return Double.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Boolean.class)) {
			return Boolean.valueOf(fromFieldValue);
		} else if (coerceToType.equals(String.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Date.class)) {
			return stringToDateConversion(fromFieldValue, coerceToType, coerceToFormat, dateFormatConfigKey);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object doubleToOtherTypeConversion(Double fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		double fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fieldValue;
		} else if (coerceToType.equals(Character.class)) {
			return (char) fieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fieldValue;
		} else if (coerceToType.equals(String.class)) {
			return DECIMAL_FORMAT.format(fieldValue);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Double.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object floatToOtherTypeConversion(Float fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		float fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fieldValue;
		} else if (coerceToType.equals(Character.class)) {
			return (char) fieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			String returnValue = String.valueOf(fromFieldValue);
			if (returnValue.indexOf(EXPONENT_NOTATION) >= 0) {
				return DECIMAL_FORMAT.format(fieldValue);
			} else {
				return returnValue;
			}
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Float.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object longToOtherTypeConversion(Long fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		long fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fieldValue;
		} else if (coerceToType.equals(Character.class)) {
			return (char) fieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fromFieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			return String.valueOf(fromFieldValue);
		} else if (coerceToType.equals(Date.class)) {
			return longToDateConversion(fromFieldValue, coerceToType);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Long.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object charToOtherTypeConversion(Character fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		char fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fromFieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fromFieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fromFieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			return String.valueOf(fromFieldValue);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Character.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object shortToOtherTypeConversion(Short fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		short fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Character.class)) {
			return (char) fieldValue;
		} else if (coerceToType.equals(Integer.class)) {
			return (int) fromFieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fromFieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fromFieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			return String.valueOf(fromFieldValue);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Short.class)) {
			return fieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object intToOtherTypeConversion(Integer fromFieldValue, Class coerceToType, Integer scale)
	        throws UnsupportedCoerceException, InvalidSituationException {
		int fieldValue = fromFieldValue;
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Short.class)) {
			return (short) fieldValue;
		} else if (coerceToType.equals(Character.class)) {
			return (char) fieldValue;
		} else if (coerceToType.equals(Byte.class)) {
			return (byte) fieldValue;
		} else if (coerceToType.equals(Long.class)) {
			return (long) fromFieldValue;
		} else if (coerceToType.equals(Float.class)) {
			return (float) fromFieldValue;
		} else if (coerceToType.equals(Double.class)) {
			return (double) fromFieldValue;
		} else if (coerceToType.equals(String.class)) {
			return String.valueOf(fromFieldValue);
		} else if (coerceToType.equals(BigDecimal.class)) {
			BigDecimal bigDecimal = new BigDecimal(fromFieldValue);
			if (scale != null) {
				bigDecimal = bigDecimal.setScale(scale);
			}

			return bigDecimal;
		} else if (coerceToType.equals(Integer.class)) {
			return fromFieldValue;
		} else if (coerceToType.equals(Boolean.class)) {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object longToDateConversion(Long fromFieldValue, Class coerceToType) {
		return new Date(fromFieldValue);
	}

	public static Object dateToStringConversion(Date fromFieldValue, Class coerceToType, String coerceToFormat)
	        throws UnsupportedCoerceException, DateParseException {
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(String.class)) {
			if (coerceToFormat == null || coerceToFormat.isEmpty()) {
				throw new DateParseException("Date format cannot be null or empty.");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat(coerceToFormat);
				return sdf.format(fromFieldValue);
			}
		} else if (coerceToType.equals(Date.class)) {
			return fromFieldValue;
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Date stringToDateConversion(String fromFieldValue, Class coerceToType, String coerceToFormat, String configKey)
	        throws DateParseException, UnsupportedCoerceException, ImproperValidationException, InvalidConfigValueException {
		if (fromFieldValue == null) {
			return null;
		} else if (coerceToType.equals(Date.class)) {
			if (coerceToFormat == null || coerceToFormat.isEmpty()) {
				throw new DateParseException("Date format cannot be null or empty.");
			}
			return MultiDateFormatUtil.stringToMultiDateFormat(fromFieldValue, coerceToFormat, configKey);
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object booleanToOtherConversion(Boolean fieldValue, Class coerceToType) throws InvalidSituationException {
		if (fieldValue == null) {
			return null;
		} else if (coerceToType.equals(String.class)) {
			return String.valueOf(fieldValue);
		} else if (coerceToType.equals(Boolean.class)) {
			return fieldValue;
		} else {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		}
	}

	public static Object dateToOtherConversion(Date fieldValue, Class coerceToType, String coerceToFormat)
	        throws InvalidSituationException, UnsupportedCoerceException, DateParseException {
		if (fieldValue == null) {
			return null;
		} else if (coerceToType.equals(Long.class)) {
			return fieldValue.getTime();
		} else if (coerceToType.equals(String.class)) {
			return dateToStringConversion(fieldValue, coerceToType, coerceToFormat);
		} else if (coerceToType.equals(Date.class)) {
			return fieldValue;
		} else {
			throw new InvalidSituationException(ExceptionMessage.INVALID_SITUATION);
		}
	}

	public static Object convertFromSQLDateToJavaDate(java.sql.Date fieldValue) {
		return new Date(fieldValue.getTime());
	}

	public static Object comvertTimeStampToDate(Timestamp fieldValue) {
		return new Date(fieldValue.getTime());
	}

	public static Object convertToGeneralType(Object fieldValue) {
		if (fieldValue == null) {
			return null;
		} else if (fieldValue.getClass().equals(java.sql.Timestamp.class)) {
			return comvertTimeStampToDate((java.sql.Timestamp) fieldValue);
		} else if (fieldValue.getClass().equals(java.sql.Date.class)) {
			return convertFromSQLDateToJavaDate((java.sql.Date) fieldValue);
		} else if (fieldValue.getClass().equals(java.math.BigInteger.class)) {
			return ((java.math.BigInteger) fieldValue).longValue();
		} else if (fieldValue.getClass().equals(java.sql.Time.class)) {
			return convertSQLTimeToDate((java.sql.Time) fieldValue);
		} else {
			return fieldValue;
		}
	}

	public static Object convertToSqlTypes(Object value) {
		if (value == null) {
			return null;
		} else if (value.getClass().equals(Date.class)) {
			return new java.sql.Timestamp(((Date) value).getTime());
		} else {
			return value;
		}
	}

	public static Object convertFromRethinkDBTypes(Object value) {
		if (value == null) {
			return null;
		} else if (value.getClass().equals(Date.class)) {
			return new java.sql.Timestamp(((Date) value).getTime());
		} else {
			return value;
		}
	}

	private static Object convertSQLTimeToDate(Time fieldValue) {
		return new Date(fieldValue.getTime());
	}

	public static Object offsetDateTypeToDateConversion(Object fieldValue, Class fieldType, Class fieldToType, ZoneOffset timeZoneOffset)
	        throws UnsupportedCoerceException {
		if (fieldValue == null) {
			return null;
		} else if (fieldToType.equals(Date.class)) {
			Instant instant = ((OffsetDateTime) fieldValue).withOffsetSameInstant(timeZoneOffset).toInstant();
			Date date = Date.from(instant);
			return date;
		} else if (fieldToType.equals(String.class)) {
			Instant instant = ((OffsetDateTime) fieldValue).withOffsetSameInstant(timeZoneOffset).toInstant();
			Date date = Date.from(instant);
			return date.toString();
		} else if (fieldToType.equals(Long.class)) {
			Instant instant = ((OffsetDateTime) fieldValue).withOffsetSameInstant(timeZoneOffset).toInstant();
			Date date = Date.from(instant);
			return date.getTime();
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object dateToOffsetDateTypeConversion(Object fieldValue, Class fieldType, Class fieldToType, ZoneOffset zoneOffset)
	        throws UnsupportedCoerceException {
		if (fieldValue == null) {
			return null;
		} else if (fieldValue.getClass().equals(Date.class) && fieldToType.equals(OffsetDateTime.class)) {
			Instant instant = ((Date) fieldValue).toInstant();
			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneOffset.of(zoneOffset.toString()));
			return offsetDateTime;
		} else {
			throw new UnsupportedCoerceException(ExceptionMessage.UNSUPPORTED_COERCE);
		}
	}

	public static Object getNegativeValue(Object value, String field, ConfigBean configBean) throws UnsupportedCoerceException {
		if (value == null) {
			return null;
		} else {
			Class<?> valueType = value.getClass();

			if (valueType.equals(Short.class)) {
				return (Short) value * -1;
			} else if (valueType.equals(Integer.class)) {
				return (Integer) value * -1;
			} else if (valueType.equals(Byte.class)) {
				return (Byte) value * -1;
			} else if (valueType.equals(Long.class)) {
				return (Long) value * -1l;
			} else if (valueType.equals(Float.class)) {
				return (Float) value * -1.0f;
			} else if (valueType.equals(Double.class)) {
				return (Double) value * -1.0;
			} else if (valueType.equals(BigDecimal.class)) {
				return ((BigDecimal) value).multiply(new BigDecimal(-1));
			} else {
				throw new UnsupportedCoerceException(
				        MessageFormat.format(ExceptionMessage.CANNOT_FIND_NEGETIVE, value, valueType, field, configBean.getConfigName(), configBean.getName(),
				                configBean.getSeekDetails().getSourceName(), configBean.getSeekDetails().getFrom(), configBean.getSeekDetails().getTo()));
			}
		}
	}
}
