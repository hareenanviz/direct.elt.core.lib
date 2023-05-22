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
public class MSSQLStoreDataTypeUtil extends StoreDataTypeUtil {

	@SuppressWarnings("rawtypes")
	@Override
	public int[] getDataTypeSize(AnvizentDataType anvizentDataType) throws UnsupportedException {
		DataType sparkType = anvizentDataType.getSparkType();
		Class javaType = anvizentDataType.getJavaType();

		if (sparkType.equals(DataTypes.ByteType)) {
			return null;
		} else if (sparkType.equals(DataTypes.ShortType)) {
			return null;
		} else if (sparkType.equals(DataTypes.StringType) && javaType.equals(Character.class)) {
			int[] sizes = new int[1];
			sizes[0] = 1;
			return sizes;
		} else if (sparkType.equals(DataTypes.IntegerType)) {
			return null;
		} else if (sparkType.equals(DataTypes.LongType)) {
			return null;
		} else if (sparkType.equals(DataTypes.FloatType)) {
			int[] sizes = new int[1];
			sizes[0] = 53;
			return sizes;
		} else if (sparkType.equals(DataTypes.DoubleType)) {
			int[] sizes = new int[2];
			sizes[0] = 18;
			sizes[1] = 12;
			return sizes;
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
		} else if (sparkType.equals(DataTypes.DateType) || sparkType.equals(DataTypes.TimestampType)) {
			return null;
		} else if (sparkType.equals(DataTypes.BooleanType)) {
			return null;
		} else {
			throw new UnsupportedException();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AnvizentEnum getDataTypeConstant(AnvizentDataType anvizentDataType) throws UnsupportedException {
		DataType sparkType = anvizentDataType.getSparkType();
		Class javaType = anvizentDataType.getJavaType();

		if (sparkType.equals(DataTypes.ByteType)) {
			return MSSQLStoreDataType.TINYINT;
		} else if (sparkType.equals(DataTypes.ShortType)) {
			return MSSQLStoreDataType.SMALLINT;
		} else if (sparkType.equals(DataTypes.StringType) && javaType.equals(Character.class)) {
			return MSSQLStoreDataType.CHAR;
		} else if (sparkType.equals(DataTypes.IntegerType)) {
			return MSSQLStoreDataType.INT;
		} else if (sparkType.equals(DataTypes.LongType)) {
			return MSSQLStoreDataType.BIGINT;
		} else if (sparkType.equals(DataTypes.FloatType)) {
			return MSSQLStoreDataType.FLOAT;
		} else if (sparkType.equals(DataTypes.DoubleType) || javaType.equals(BigDecimal.class)) {
			return MSSQLStoreDataType.DECIMAL;
		} else if (sparkType.equals(DataTypes.StringType)) {
			return MSSQLStoreDataType.VARCHAR;
		} else if (sparkType.equals(DataTypes.DateType) || sparkType.equals(DataTypes.TimestampType)) {
			return MSSQLStoreDataType.DATETIME;
		} else if (sparkType.equals(DataTypes.BooleanType)) {
			return MSSQLStoreDataType.BIT;
		} else {
			throw new UnsupportedException();
		}
	}

}
