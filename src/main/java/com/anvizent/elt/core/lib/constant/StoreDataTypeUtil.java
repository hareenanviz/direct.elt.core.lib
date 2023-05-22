package com.anvizent.elt.core.lib.constant;

import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.exception.UnsupportedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class StoreDataTypeUtil {

	public abstract int[] getDataTypeSize(AnvizentDataType anvizentDataType) throws UnsupportedException;

	public abstract AnvizentEnum getDataTypeConstant(AnvizentDataType anvizentDataType) throws UnsupportedException;

	public String getDataType(AnvizentDataType anvizentDataType) throws UnsupportedException {
		int[] dataTypeSize = getDataTypeSize(anvizentDataType);
		String size;

		if (dataTypeSize == null || dataTypeSize.length == 0) {
			size = "";
		} else if (dataTypeSize.length == 2) {
			size = "(" + dataTypeSize[0] + "," + dataTypeSize[1] + ")";
		} else {
			size = "(" + dataTypeSize[0] + ")";
		}

		return getDataTypeConstant(anvizentDataType).getValue() + size;
	}
}