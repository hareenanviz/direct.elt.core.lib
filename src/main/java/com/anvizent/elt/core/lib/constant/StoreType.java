package com.anvizent.elt.core.lib.constant;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public enum StoreType implements AnvizentEnum {

	MYSQL(Constants.MYSQL_DRIVER, '`', '`'), MSSQL(Constants.MSSQL_DRIVER, '[', ']'), ORACLE(Constants.ORACLE_DRIVER, '"', '"');

	private String driver;
	private char startMetaCharacter;
	private char endMetaCharacter;

	private StoreType(String driver, char startMetaCharacter, char endMetaCharacter) {
		this.driver = driver;
		this.startMetaCharacter = startMetaCharacter;
		this.endMetaCharacter = endMetaCharacter;
	}

	public static LinkedHashMap<StoreType, StoreDataTypeUtil> FACTORIES = new LinkedHashMap<StoreType, StoreDataTypeUtil>() {
		private static final long serialVersionUID = 1L;

		{
			put(StoreType.MYSQL, new MySQLStoreDataTypeUtil());
			put(StoreType.MSSQL, new MSSQLStoreDataTypeUtil());
			put(StoreType.ORACLE, new OracleStoreDataTypeUtil());
		}
	};

	public static LinkedHashMap<StoreType, List<Integer>> DATA_CORRUPTED_ERROR_CODES = new LinkedHashMap<StoreType, List<Integer>>() {
		private static final long serialVersionUID = 1L;

		{
			put(StoreType.MYSQL, getMySQLErrorCodes());
		}

		private List<Integer> getMySQLErrorCodes() {
			return Arrays.asList(1054, 1059, 1063, 1064, 1071, 1110, 1118, 1138, 1162, 1166, 1171, 1230, 1231, 1232, 1261, 1262, 1263, 1264, 1265, 1365, 1366,
			        1367, 1406, 1425, 1426, 1427, 1453, 1458, 1690);
		}
	};

	public char getStartMetaCharacter() {
		return startMetaCharacter;
	}

	public char getEndMetaCharacter() {
		return endMetaCharacter;
	}

	public static StoreType getInstance(String driver) {
		return getInstance(driver, null);
	}

	public static StoreType getInstance(String driver, StoreType defaultValue) {
		if (driver == null || driver.isEmpty()) {
			return null;
		} else if (driver.equals(Constants.MYSQL_DRIVER)) {
			return MYSQL;
		} else if (driver.equals(Constants.MSSQL_DRIVER)) {
			return MSSQL;
		} else if (driver.equals(Constants.ORACLE_DRIVER)) {
			return ORACLE;
		} else {
			return defaultValue;
		}
	}

	@Override
	public String getValue() {
		return driver;
	}

}
