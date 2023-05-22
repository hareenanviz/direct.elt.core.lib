package com.anvizent.elt.core.lib.constant;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public enum StatsCategory {

	IN("IN"), OUT("OUT"), ERROR("ERROR"), OTHER("OTHER");

	private String value;

	private StatsCategory(String value) {
		this.value = value;
	}

	public static StatsCategory getInstance(String value) {
		return getInstance(value, null);
	}

	private static StatsCategory getInstance(String value, StatsCategory defaultValue) {
		if (value == null || value.isEmpty()) {
			return defaultValue;
		} else if (value.equalsIgnoreCase(IN.value)) {
			return IN;
		} else if (value.equalsIgnoreCase(OUT.value)) {
			return OUT;
		} else if (value.equalsIgnoreCase(ERROR.value)) {
			return ERROR;
		} else if (value.equalsIgnoreCase(OTHER.value)) {
			return OTHER;
		} else {
			return null;
		}
	}
}
