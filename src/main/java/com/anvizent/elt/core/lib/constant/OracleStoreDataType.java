package com.anvizent.elt.core.lib.constant;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public enum OracleStoreDataType implements AnvizentEnum {

	INT("INT"), VARCHAR("VARCHAR"), DECIMAL("DECIMAL"), INTEGER("INTEGER"), SMALLINT("SMALLINT"), NUMERIC("NUMERIC"), LONG("LONG"), FLOAT("FLOAT"), DOUBLE(
			"DOUBLE"), DATE("DATE"), TIMESTAMP(
					"TIMESTAMP"), CHAR("CHAR"), BINARY_FLOAT("BINARY_FLOAT"), BINARY_DOUBLE("BINARY_DOUBLE"), VARBINARY("VARBINARY"), TEXT("TEXT");
	// TODO add

	private String value;

	private OracleStoreDataType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
