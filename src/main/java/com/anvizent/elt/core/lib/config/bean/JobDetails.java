package com.anvizent.elt.core.lib.config.bean;

import java.io.Serializable;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class JobDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jobName;
	private String componentName;
	private String componentConfigName;
	private String internalRDDName;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentConfigName() {
		return componentConfigName;
	}

	public void setComponentConfigName(String componentConfigName) {
		this.componentConfigName = componentConfigName;
	}

	public String getInternalRDDName() {
		return internalRDDName;
	}

	public void setInternalRDDName(String internalRDDName) {
		this.internalRDDName = internalRDDName;
	}

	public JobDetails(String jobName, String componentName, String componentConfigName, String internalRDDName) {
		this.jobName = jobName;
		this.componentName = componentName;
		this.componentConfigName = componentConfigName;
		this.internalRDDName = internalRDDName;
	}

}
