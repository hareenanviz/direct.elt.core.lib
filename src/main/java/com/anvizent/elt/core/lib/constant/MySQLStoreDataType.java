package com.anvizent.elt.core.lib.constant;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public enum MySQLStoreDataType implements AnvizentEnum {
	INT("INT"), VARCHAR("VARCHAR"), DECIMAL("DECIMAL"), BIGINT("BIGINT"), INTEGER("INTEGER"), SMALLINT("SMALLINT"), TINYINT("TINYINT"), MEDIUMINT(
				"MEDIUMINT"), NUMERIC("NUMERIC"), FLOAT("FLOAT"), DOUBLE("DOUBLE"), BIT("BIT"), DATE("DATE"), DATETIME(
						"DATETIME"), TIMESTAMP("DATETIME"), TIME("TIME"), YEAR("YEAR"), CHAR("CHAR"), BINARY("BINARY"), VARBINARY("VARBINARY"), TEXT("TEXT");

	private String value;

	private MySQLStoreDataType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
