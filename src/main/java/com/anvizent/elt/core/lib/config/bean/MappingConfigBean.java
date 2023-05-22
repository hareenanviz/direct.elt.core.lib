package com.anvizent.elt.core.lib.config.bean;

import java.io.Serializable;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public abstract class MappingConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String componentName;
	private String configName;
	private String mappingConfigName;
	private SeekDetails seekDetails;
	private int maxRetryCount;
	private Long retryDelay;

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getMappingConfigName() {
		return mappingConfigName;
	}

	public void setMappingConfigName(String mappingConfigName) {
		this.mappingConfigName = mappingConfigName;
	}

	public SeekDetails getSeekDetails() {
		return seekDetails;
	}

	public void setSeekDetails(SeekDetails seekDetails) {
		this.seekDetails = seekDetails;
	}

	public String getFullConfigName() {
		return mappingConfigName + " of " + configName;
	}

	public int getMaxRetryCount() {
		return maxRetryCount;
	}

	public void setMaxRetryCount(int maxRetryCount) {
		this.maxRetryCount = maxRetryCount;
	}

	public Long getRetryDelay() {
		return retryDelay;
	}

	public void setRetryDelay(Long retryDelay) {
		this.retryDelay = retryDelay;
	}
}