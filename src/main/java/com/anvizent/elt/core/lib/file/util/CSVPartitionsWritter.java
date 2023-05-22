package com.anvizent.elt.core.lib.file.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

public class CSVPartitionsWritter {

	public static void writeTo(String path, HashMap<String, Object> row) throws IOException {
		CsvWriter csvWriter = new CsvWriter(new FileWriter(path), new CsvWriterSettings());
		csvWriter.writeRow(row.values());
		csvWriter.flush();
		csvWriter.close();
	}

}
