package com.anvizent.elt.core.lib.constant;

import java.math.BigDecimal;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.DecimalType;

import com.anvizent.elt.core.lib.AnvizentDataType;
import com.anvizent.elt.core.lib.exception.UnsupportedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class OracleStoreDataTypeUtil extends StoreDataTypeUtil {

	@SuppressWarnings("rawtypes")
	@Override
	public int[] getDataTypeSize(AnvizentDataType anvizentDataType) throws UnsupportedException {
		DataType sparkType = anvizentDataType.getSparkType();
		Class javaType = anvizentDataType.getJavaType();
		if (sparkType.equals(DataTypes.ByteType)) {
			/*
			 * int[] sizes = new int[1]; sizes[0] = 4;
			 */
			return null;
		} else if (sparkType.equals(DataTypes.ShortType)) {
			/*
			 * int[] sizes = new int[1]; sizes[0] = 6;
			 */
			return null;
		} else if (sparkType.equals(DataTypes.StringType) && javaType.equals(Character.class)) {
			int[] sizes = new int[1];
			sizes[0] = 1;
			return sizes;
		} else if (sparkType.equals(DataTypes.IntegerType)) {
			/*
			 * int[] sizes = new int[1]; sizes[0] = 11;
			 */
			return null;
		} else if (sparkType.equals(DataTypes.LongType)) {
			/*
			 * int[] sizes = new int[1]; sizes[0] = 20;
			 */
			return null;
		} else if (sparkType.equals(DataTypes.FloatType)) {
			return null;
		} else if (sparkType.equals(DataTypes.DoubleType)) {
			return null;
			// TODO
		} else if (javaType.equals(BigDecimal.class)) {
			DecimalType decimalType = (DecimalType) sparkType;
			int[] sizes = new int[2];
			sizes[0] = decimalType.precision();
			sizes[1] = decimalType.scale();
			return sizes;
		} else if (sparkType.equals(DataTypes.StringType)) {
			int[] sizes = new int[1];
			sizes[0] = 255;
			return sizes;
		} else if (sparkType.equals(DataTypes.DateType)) {
			return null;
		} else if (sparkType.equals(DataTypes.TimestampType)) {
			return null;
		} else if (sparkType.equals(DataTypes.BooleanType)) {
			int[] sizes = new int[1];
			sizes[0] = 1;
			return sizes;
		} else {
			throw new UnsupportedException();
		}

		// TODO check with datatypes and sizes
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AnvizentEnum getDataTypeConstant(AnvizentDataType anvizentDataType) throws UnsupportedException {
		DataType sparkType = anvizentDataType.getSparkType();
		Class javaType = anvizentDataType.getJavaType();

		if (sparkType.equals(DataTypes.ByteType)) {
			return OracleStoreDataType.SMALLINT;
		} else if (sparkType.equals(DataTypes.ShortType)) {
			return OracleStoreDataType.SMALLINT;
		} else if (sparkType.equals(DataTypes.StringType) && javaType.equals(Character.class)) {
			return OracleStoreDataType.CHAR;
		} else if (sparkType.equals(DataTypes.IntegerType)) {
			return OracleStoreDataType.INT;
		} else if (sparkType.equals(DataTypes.LongType)) {
			return OracleStoreDataType.LONG;
		} else if (sparkType.equals(DataTypes.FloatType)) {
			return OracleStoreDataType.FLOAT;
		} else if (sparkType.equals(DataTypes.DoubleType)) {
			return OracleStoreDataType.DECIMAL;
		} else if (javaType.equals(BigDecimal.class)) {
			return OracleStoreDataType.DECIMAL;
		} else if (sparkType.equals(DataTypes.StringType)) {
			return OracleStoreDataType.VARCHAR;
		} else if (sparkType.equals(DataTypes.DateType)) {
			return OracleStoreDataType.DATE;
		} else if (sparkType.equals(DataTypes.TimestampType)) {
			return OracleStoreDataType.TIMESTAMP;
		} else if (sparkType.equals(DataTypes.BooleanType)) {
			return OracleStoreDataType.CHAR;
		} else {
			throw new UnsupportedException();
		}

		// TODO check with datatypes and sizes
	}

}
