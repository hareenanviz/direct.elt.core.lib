package com.anvizent.elt.core.lib.function;

import java.util.Date;

import org.apache.spark.TaskContext;

import com.anvizent.elt.core.lib.config.bean.ConfigBean;

public class LogUtil {

	public static synchronized void printUsage(ConfigBean configBean) {
		int mb = 1024 * 1024;
		int gb = mb * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics " + new Date() + " #####");

		if (configBean != null) {
			System.out.println("ConfigName: '" + configBean.getConfigName() + "'");
			System.out.println("Name: '" + configBean.getName() + "'");
			System.out.println("SeekDetails: '" + configBean.getSeekDetails() + "'");
			System.out.println("PartitionId: " + TaskContext.getPartitionId() + "'");
		}

		System.out.println(
		        "Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb + "mb(" + (runtime.totalMemory() - runtime.freeMemory()) / gb + "gb)");
		System.out.println("Free Memory:" + runtime.freeMemory() / mb + "mb(" + runtime.freeMemory() / gb + "gb)");
		System.out.println("Total Memory:" + runtime.totalMemory() / mb + "mb(" + runtime.totalMemory() / gb + "gb)");
		System.out.println("Max Memory:" + runtime.maxMemory() / mb + "mb(" + runtime.maxMemory() / gb + "gb)");
	}

}
