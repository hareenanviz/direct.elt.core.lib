package com.anvizent.elt.core.lib.util;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.anvizent.elt.core.lib.exception.ImproperValidationException;
import com.anvizent.elt.core.lib.exception.InvalidConfigValueException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ConfigUtil {

	public static ArrayList<String> getArrayList(CSVRecord csvRecord) {
		ArrayList<String> arrayList = new ArrayList<String>();

		for (int i = 0; i < csvRecord.size(); i++) {
			arrayList.add(csvRecord.get(i) == null ? "" : csvRecord.get(i).trim());
		}

		return arrayList;
	}

	public static CSVRecord getCSVRecord(String s, char delimeter, String key) throws ImproperValidationException, InvalidConfigValueException {
		try {
			CSVFormat format = CSVFormat.RFC4180.withDelimiter(delimeter).withQuote('"');
			CSVParser csvParser = CSVParser.parse(s, format);
			CSVRecord csvRecord = csvParser.getRecords().get(0);
			return csvRecord;
		} catch (NullPointerException exception) {
			throw new ImproperValidationException();
		} catch (IOException | IndexOutOfBoundsException exception) {
			throw new InvalidConfigValueException(key, s);
		}
	}
}