package com.anvizent.elt.core.lib.constant;

import org.apache.spark.sql.types.DataTypes;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class Constants {

	public static final String DECIMAL_DATA_TYPE = DataTypes.createDecimalType().typeName().substring(0, 7).toLowerCase();
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public static final int DECIMAL_PRECISION = 10;
	public static final int DECIMAL_SCALE = 5;
	public static final String UNKNOWN = "UNKNOWN";

	public static class ExceptionMessage {
		public static final String TYPE_NOT_SUPPORTED = "Type not supported.";
		public static final String STORE_DATE_TYPE_NOT_IMPLEMENTED = "Store Date Type is not implemented.";
		public static final String STORE_TYPE_NOT_SUPPORTED = "Store Type is not supported.";
		public static final String IMPROPER_VALIDATION_FROM_DEV_TEAM = "Improper validation from dev team!";
		public static final String INVALID_CONFIG_VALUE = "Invalid config value ''{0}'' for the key ''{1}''";
		public static final String UNSUPPORTED_COERCE = "Unsupported coerce.";
		public static final String CANNOT_FIND_NEGETIVE = "Can not find a negetive value of ''{0}'' of type ''{1}'' "
		        + "for the field ''{2}'' of the component ''{3}'' with name ''{4}'' which is specified in ''{5}'' between {6} & {7} lines/records.";
		public static final String INVALID_SITUATION = "Invalid situation.";
		public static final String PARSE_EXCEPTION_MESSAGE = "Unable to pase string: ''{0}''. It is not matched with any date format";
	}

	public static class StatsNames {
		public static final String IN = "IN";
		public static final String OUT = "OUT";
		public static final String READ = "READ";
		public static final String WRITTEN = "WRITTEN";
		public static final String INSERTED = "INSERTED";
		public static final String UPDATED = "UPDATED";
		public static final String ERROR = "ERROR";
		public static final String LOOKUP_COUNT = "LOOKUP_COUNT";
		public static final String LOOKEDUP_ROWS = "LOOKEDUP_ROWS";
		public static final String CACHE = "CACHE";
		public static final String FILTERED = "FILTERED";
		public static final String REMOVED = "REMOVED";
	}

	public static class General {
		public static final String PLACEHOLDER = "${key}";
		public static final String CONFIG_SEPARATOR = ",";
		public static final String KEY_SEPARATOR = ".";
		public static final String NEW_LINE = "\n";
		public static final String TAB = "\t";
		public static final String DATE_FORMAT = "yyyy-MM-dd";
		public static final String TIME_FORMAT = "HH:ss:mm";
		public static final String SQL_TYPES = "SQL_TYPES";
		public static final String JAVA_TYPES = "JAVA_TYPES";
		public static final String STRUCTURE = "Structure";
		public static final String COLON = ":";
		public static final String OPEN_PARENTHESIS = "(";
		public static final String CLOSE_PARENTHESIS = ")";
		public static final String OPEN_BRACE = "{";
		public static final String CLOSE_BRACE = "}";
		public static final String PROPERTY_COMMENT = "#";
		public static final String WHERE_FIELDS_PLACEHOLDER_START = "$f{";
		public static final String WHERE_FIELDS_PLACEHOLDER_END = "}";
		public static final char AMP = '&';
		public static final char QUERY_STRING = '?';
		@SuppressWarnings("rawtypes")
		public static final Class DEFAULT_TYPE_FOR_FILTER_BY_REGEX = String.class;
		public static final String DERIVED_COMPONENT = "derivedcomponent";
		public static final String DERIVED_COMPONENT_NAME_REGEX = "[a-zA-Z0-9]{1,}";
		public static final String LOOKUP_CACHED = "CACHED";
		public static final String LOOKUP_RESULTING_ROW = "RESULTING_ROW";
		public static final String LOOKEDUP_ROWS_COUNT = "LOOKEDUP_ROWS_COUNT";
		public static final String RECORD_EXISTS = "RECORD_EXISTS";
		public static final String DO_UPDATE = "DO_UPDATE";
		public static final String USE_UNICODE = "useUnicode=yes";
		public static final String CHARSET_ENCODING = "characterEncoding=UTF-8";
		public static final String REWRITE_BATCHED_STATEMENTS = "rewriteBatchedStatements=true";
		public static final String NO_PARTITION = "NO_PARTITION";
		public static final String RANGE_PARTITION_TYPE = "RANGE_PARTITION_TYPE";
		public static final String DISTINCT_FIELDS_PARTITION_TYPE = "DISTINCT_FIELDS_PARTITION_TYPE";

		public static class Operator {
			public static final char EQUAL_TO = '=';
			public static final String NOT_EQUAL_TO = "!=";
		}
	}
}
