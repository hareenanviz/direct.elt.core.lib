package com.anvizent.elt.core.lib;

import java.io.Serializable;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.util.DoubleAccumulator;

import com.anvizent.elt.core.lib.constant.StatsCategory;
import com.anvizent.elt.core.lib.stats.calculator.Anvizent2StatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseAnvizentSymmetricStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseMapToPairStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseSQLBatchStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseVoidStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.IStatsCalculator;
import com.anvizent.elt.core.lib.stats.calculator.BaseRetrievalStatsCalculator;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
@SuppressWarnings({ "rawtypes" })
public class AnvizentAccumulator implements Serializable {

	private static final long serialVersionUID = 1L;

	private DoubleAccumulator doubleAccumulator;
	private String internalRDDName;
	private String specialName;
	private StatsCategory statsCategory;
	private String statsName;
	private boolean componentLevel;
	private IStatsCalculator iStatsCalculator;

	public DoubleAccumulator getDoubleAccumulator() {
		return doubleAccumulator;
	}

	public void setDoubleAccumulator(DoubleAccumulator doubleAccumulator) {
		this.doubleAccumulator = doubleAccumulator;
	}

	public String getInternalRDDName() {
		return internalRDDName;
	}

	public void setInternalRDDName(String internalRDDName) {
		this.internalRDDName = internalRDDName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public StatsCategory getStatsCategory() {
		return statsCategory;
	}

	public void setStatsCategory(StatsCategory statsCategory) {
		this.statsCategory = statsCategory;
	}

	public String getStatsName() {
		return statsName;
	}

	public void setStatsName(String statsName) {
		this.statsName = statsName;
	}

	public boolean isComponentLevel() {
		return componentLevel;
	}

	public void setComponentLevel(boolean componentLevel) {
		this.componentLevel = componentLevel;
	}

	public BaseStatsCalculator getStatsCalculator() {
		return (BaseStatsCalculator) iStatsCalculator;
	}

	public BaseVoidStatsCalculator getVoidStatsCalculator() {
		return (BaseVoidStatsCalculator) iStatsCalculator;
	}

	public BaseRetrievalStatsCalculator getSqlRetrievalStatsCalculator() {
		return (BaseRetrievalStatsCalculator) iStatsCalculator;
	}

	public BaseMapToPairStatsCalculator getMapToPairStatsCalculator() {
		return (BaseMapToPairStatsCalculator) iStatsCalculator;
	}

	public BaseAnvizentSymmetricStatsCalculator getBaseAnvizentSymmetricStatsCalculator() {
		return (BaseAnvizentSymmetricStatsCalculator) iStatsCalculator;
	}

	public Anvizent2StatsCalculator getAnvizent2StatsCalculator() {
		return (Anvizent2StatsCalculator) iStatsCalculator;
	}

	public BaseSQLBatchStatsCalculator getBaseSQLBatchStatsCalculator() {
		return (BaseSQLBatchStatsCalculator) iStatsCalculator;
	}

	public AnvizentAccumulator(SparkSession sparkSession, String accumulatorName, String internalRDDName, StatsCategory statsCategory, String statsName,
			boolean componentLevel, IStatsCalculator statsCalulator) {
		this.doubleAccumulator = sparkSession.sparkContext().doubleAccumulator(accumulatorName + "_" + internalRDDName);
		this.internalRDDName = internalRDDName;
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.componentLevel = componentLevel;
		this.iStatsCalculator = statsCalulator;
	}

	public AnvizentAccumulator(SparkSession sparkSession, String accumulatorName, String internalRDDName, String specialName, StatsCategory statsCategory,
			String statsName, boolean componentLevel, IStatsCalculator statsCalulator) {
		this.doubleAccumulator = sparkSession.sparkContext().doubleAccumulator(accumulatorName + "_" + internalRDDName + "_" + specialName);
		this.internalRDDName = internalRDDName;
		this.specialName = specialName;
		this.statsCategory = statsCategory;
		this.statsName = statsName;
		this.componentLevel = componentLevel;
		this.iStatsCalculator = statsCalulator;
	}
}