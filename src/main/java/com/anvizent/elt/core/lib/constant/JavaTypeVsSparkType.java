package com.anvizent.elt.core.lib.constant;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.DecimalType;

public enum JavaTypeVsSparkType {

	LONG(DataTypes.LongType, Long.class), BOOLEAN(DataTypes.BooleanType, Boolean.class), STRING(DataTypes.StringType, String.class),
	DATE(DataTypes.DateType, Date.class), DOUBLE(DataTypes.DoubleType, Double.class), FLOAT(DataTypes.FloatType, Float.class),
	INTEGER(DataTypes.IntegerType, Integer.class), TIME(DataTypes.TimestampType, Time.class), TIMESTAMP(DataTypes.TimestampType, Timestamp.class),
	SHORT(DataTypes.ShortType, Short.class), BIG_DECIMAL(new DecimalType(), BigDecimal.class);

	private static HashMap<Class<?>, JavaTypeVsSparkType> dataByJavaType = new HashMap<>();

	static {
		for (JavaTypeVsSparkType sparkTypeVsJavaType : JavaTypeVsSparkType.values()) {
			dataByJavaType.put(sparkTypeVsJavaType.getJavaType(), sparkTypeVsJavaType);
		}
	}

	private String sparkTypeAsString;
	private DataType dataType;
	private Class<?> javaType;

	public String getSparkTypeAsString() {
		return sparkTypeAsString;
	}

	public DataType getDataType() {
		return dataType;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	private JavaTypeVsSparkType(DataType dataType, Class<?> javaType) {
		this.dataType = dataType;
		this.javaType = javaType;
	}

	public static JavaTypeVsSparkType getInstance(Class<?> javaType) {
		return dataByJavaType.get(javaType);
	}

}
