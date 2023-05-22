package com.anvizent.elt.core.lib.constant;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public enum MSSQLStoreDataType implements AnvizentEnum {
	BIT("BIT"), TINYINT("TINYINT"), SMALLINT("SMALLINT"), INT("INT"), BIGINT("BIGINT"), DECIMAL("DECIMAL"), NUMERIC("NUMERIC"), FLOAT("FLOAT"), DATETIME(
			"DATETIME"), DATE("DATE"), TIME("TIME"), NCHAR("NCHAR"), CHAR("CHAR"), VARCHAR("VARCHAR"), NVARCHAR("NVARCHAR"), BINARY("BINARY"), 
			VARBINARY("VARBINARY"), TEXT("TEXT"), NTEXT("NTEXT");

	private String value;

	private MSSQLStoreDataType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
