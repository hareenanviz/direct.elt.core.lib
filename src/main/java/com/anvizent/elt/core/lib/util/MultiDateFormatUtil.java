package com.anvizent.elt.core.lib.util;

import java.text.MessageFormat;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.csv.CSVRecord;

import com.anvizent.elt.core.lib.constant.Constants.ExceptionMessage;
import com.anvizent.elt.core.lib.exception.DateParseException;
import com.anvizent.elt.core.lib.exception.ImproperValidationException;
import com.anvizent.elt.core.lib.exception.InvalidConfigValueException;

/**
 * @author Hareen Bejjanki
 *
 */
public class MultiDateFormatUtil {

	public static Date stringToMultiDateFormat(String fromFieldValue, String formats, String configKey)
	        throws ImproperValidationException, DateParseException, InvalidConfigValueException {

		CSVRecord csvRecord = ConfigUtil.getCSVRecord(formats, '|', configKey);

		return parseToDate(fromFieldValue, ConfigUtil.getArrayList(csvRecord));
	}

	private static Date parseToDate(String fromFieldValue, ArrayList<String> formatList) throws DateParseException {
		for (String format : formatList) {
			try {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(format).toFormatter();
				try {
					ZonedDateTime offsetDateTime = ZonedDateTime.parse(fromFieldValue, formatter);
					return new Date(offsetDateTime.toInstant().toEpochMilli());
				} catch (DateTimeParseException exception) {
					if (exception.getCause().getClass().equals(DateTimeException.class)) {
						if (exception.getCause().getStackTrace()[0].getClassName().equals(ZonedDateTime.class.getName())) {
							formatter = formatter.withZone(ZoneId.systemDefault());
							ZonedDateTime offsetDateTime = ZonedDateTime.parse(fromFieldValue, formatter);
							return new Date(offsetDateTime.toInstant().toEpochMilli());
						}
					}
				}
			} catch (DateTimeParseException | ArithmeticException exception) {
			}
		}

		throw new DateParseException(MessageFormat.format(ExceptionMessage.PARSE_EXCEPTION_MESSAGE, fromFieldValue));
	}

	public static void main(String[] args) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss:SSSSSSS").toFormatter();

		System.out.println(formatter.getZone());

		if (formatter.getZone() == null) {
//			formatter = formatter.withZone(ZoneId.systemDefault());
		}

		try {
			ZonedDateTime offsetDateTime = ZonedDateTime.parse("2045-12-10 05:45:01:1674394", formatter);
			System.out.println(new Date(offsetDateTime.toInstant().toEpochMilli()));
		} catch (DateTimeParseException exception) {
			if (exception.getCause().getClass().equals(DateTimeException.class)) {
				if (exception.getCause().getStackTrace()[0].getClassName().equals(ZonedDateTime.class.getName())) {
					formatter = formatter.withZone(ZoneId.systemDefault());
					ZonedDateTime offsetDateTime = ZonedDateTime.parse("2045-12-10 05:45:01:1674394", formatter);
					System.out.println(new Date(offsetDateTime.toInstant().toEpochMilli()));
				}
				System.out.println(exception.getCause().getStackTrace()[1]);
				System.out.println(exception.getCause().getStackTrace()[2]);
				System.out.println(exception.getCause().getStackTrace()[3]);
				System.out.println(exception.getCause().getStackTrace()[4]);
			}
		}
	}
}
