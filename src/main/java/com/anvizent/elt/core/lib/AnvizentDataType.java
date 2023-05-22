package com.anvizent.elt.core.lib;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

import com.anvizent.elt.core.lib.constant.Constants;
import com.anvizent.elt.core.lib.constant.Constants.ExceptionMessage;
import com.anvizent.elt.core.lib.constant.MySQLStoreDataType;
import com.anvizent.elt.core.lib.constant.StoreType;
import com.anvizent.elt.core.lib.exception.UnimplementedException;
import com.anvizent.elt.core.lib.exception.UnsupportedException;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class AnvizentDataType implements Serializable {

	private static final long serialVersionUID = 1L;

	private DataType sparkType;
	@SuppressWarnings("rawtypes")
	private Class javaType;

	public DataType getSparkType() {
		return sparkType;
	}

	public void setSparkType(DataType sparkType) {
		this.sparkType = sparkType;
	}

	@SuppressWarnings("rawtypes")
	public Class getJavaType() {
		return javaType;
	}

	@SuppressWarnings("rawtypes")
	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public AnvizentDataType(DataType sparkType) throws UnsupportedException {
		this.sparkType = sparkType;
		setJavaType(sparkType);
	}

	private void setJavaType(DataType sparkType) throws UnsupportedException {
		this.sparkType = sparkType;

		if (sparkType.equals(DataTypes.BooleanType)) {
			this.javaType = Boolean.class;
		} else if (sparkType.equals(DataTypes.ByteType)) {
			this.javaType = Byte.class;
		} else if (sparkType.equals(DataTypes.DateType) || sparkType.equals(DataTypes.TimestampType)) {
			this.sparkType = DataTypes.TimestampType;
			this.javaType = Date.class;
		} else if (sparkType.equals(DataTypes.DoubleType)) {
			this.javaType = Double.class;
		} else if (sparkType.equals(DataTypes.FloatType)) {
			this.javaType = Float.class;
		} else if (sparkType.equals(DataTypes.IntegerType)) {
			this.javaType = Integer.class;
		} else if (sparkType.equals(DataTypes.LongType)) {
			this.javaType = Long.class;
		} else if (sparkType.equals(DataTypes.ShortType)) {
			this.javaType = Short.class;
		} else if (sparkType.equals(DataTypes.StringType)) {
			this.javaType = String.class;
		} else if (sparkType.typeName().toLowerCase().startsWith(Constants.DECIMAL_DATA_TYPE)) {
			this.javaType = BigDecimal.class;
		} else {
			throw new UnsupportedException(ExceptionMessage.TYPE_NOT_SUPPORTED);
		}

		// TODO
		/*
		 * CalendarIntervalType, BinaryType
		 */
	}

	@SuppressWarnings("rawtypes")
	public AnvizentDataType(Class javaType) throws UnsupportedException {
		this.javaType = javaType;
		this.sparkType = getSparkType(javaType, Constants.DECIMAL_PRECISION, Constants.DECIMAL_SCALE);
	}

	@SuppressWarnings("rawtypes")
	public AnvizentDataType(Class javaType, int precision, int scale) throws UnsupportedException {
		if (javaType.equals(java.sql.Date.class) || javaType.equals(java.sql.Timestamp.class) || javaType.equals(java.sql.Time.class)) {
			javaType = Date.class;
		}

		this.javaType = javaType;
		this.sparkType = getSparkType(javaType, precision, scale);
	}

	@SuppressWarnings("rawtypes")
	private DataType getSparkType(Class javaType, int precision, int scale) throws UnsupportedException {
		if (javaType.equals(Boolean.class)) {
			return DataTypes.BooleanType;
		} else if (javaType.equals(Byte.class)) {
			return DataTypes.ByteType;
		} else if (javaType.equals(Date.class) || javaType.equals(java.sql.Date.class)) {
			return DataTypes.TimestampType;
		} else if (javaType.equals(Short.class)) {
			return DataTypes.ShortType;
		} else if (javaType.equals(Integer.class)) {
			return DataTypes.IntegerType;
		} else if (javaType.equals(Long.class)) {
			return DataTypes.LongType;
		} else if (javaType.equals(Float.class)) {
			return DataTypes.FloatType;
		} else if (javaType.equals(Double.class)) {
			return DataTypes.DoubleType;
		} else if (javaType.equals(String.class) || javaType.equals(Character.class)) {
			return DataTypes.StringType;
		} else if (javaType.equals(BigDecimal.class)) {
			return DataTypes.createDecimalType(precision, scale);
		} else {
			throw new UnsupportedException(ExceptionMessage.TYPE_NOT_SUPPORTED);
		}
	}

	public AnvizentDataType(StoreType storeType, String sourceOrStoreType, String sourceOrStoreTypesSize) throws UnsupportedException, UnimplementedException {

		if (storeType.equals(StoreType.MYSQL)) {
			setSparkAndJavaType(sourceOrStoreType);
		} else {
			throw new UnimplementedException(ExceptionMessage.STORE_DATE_TYPE_NOT_IMPLEMENTED);
		}
	}

	private void setSparkAndJavaType(String sourceOrStoreType) throws UnsupportedException {
		// TODO another StoreDataTypes
		if (sourceOrStoreType.equals(MySQLStoreDataType.BIGINT.name())) {
			javaType = Long.class;
			sparkType = DataTypes.LongType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.BIT.name())) {
			javaType = Boolean.class;
			sparkType = DataTypes.BooleanType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.CHAR.name())) {
			javaType = String.class;
			sparkType = DataTypes.StringType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.DATE.name())) {
			javaType = Date.class;
			sparkType = DataTypes.DateType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.DATETIME.name())) {
			javaType = Date.class;
			sparkType = DataTypes.DateType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.DOUBLE.name()) || sourceOrStoreType.equals(MySQLStoreDataType.DECIMAL.name())) {
			javaType = Double.class;
			sparkType = DataTypes.DoubleType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.FLOAT.name())) {
			javaType = Float.class;
			sparkType = DataTypes.FloatType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.INT.name()) || sourceOrStoreType.equals(MySQLStoreDataType.INTEGER.name())) {
			javaType = Integer.class;
			sparkType = DataTypes.IntegerType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.TEXT.name()) || sourceOrStoreType.equals(MySQLStoreDataType.VARCHAR.name())) {
			javaType = String.class;
			sparkType = DataTypes.StringType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.TIME.name())) {
			javaType = Time.class;
			sparkType = DataTypes.TimestampType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.TIMESTAMP.name())) {
			javaType = Date.class;
			sparkType = DataTypes.TimestampType;
		} else if (sourceOrStoreType.equals(MySQLStoreDataType.MEDIUMINT.name()) || sourceOrStoreType.equals(MySQLStoreDataType.SMALLINT.name())
		        || sourceOrStoreType.equals(MySQLStoreDataType.TINYINT.name())) {
			javaType = Short.class;
			sparkType = DataTypes.ShortType;
		} else {
			throw new UnsupportedException(ExceptionMessage.STORE_TYPE_NOT_SUPPORTED);
		}

		// TODO
		/*
		 * VARBINARY, YEAR BINARY, NUMERIC, MEDIUMINT, SMALLINT, TINYINT,
		 * 
		 */
	}

	@Override
	public String toString() {
		return "AnvizentDataType [sparkType=" + sparkType + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((sparkType == null) ? 0 : sparkType.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		AnvizentDataType other = (AnvizentDataType) obj;
		if (javaType == null) {
			if (other.javaType != null) {
				return false;
			}
		} else if (!javaType.equals(other.javaType)) {
			return false;
		}
		if (sparkType == null) {
			if (other.sparkType != null) {
				return false;
			}
		} else if (!sparkType.equals(other.sparkType)) {
			return false;
		}

		return true;
	}

}
