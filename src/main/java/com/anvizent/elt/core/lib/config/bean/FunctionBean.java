package com.anvizent.elt.core.lib.config.bean;

import java.io.Serializable;

/**
 * @author Hareen Bejjanki
 *
 */
public class FunctionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String configName;
	private SeekDetails seekDetails;
	private int maxRetryCount;
	private Long retryDelay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public SeekDetails getSeekDetails() {
		return seekDetails;
	}

	public void setSeekDetails(SeekDetails seekDetails) {
		this.seekDetails = seekDetails;
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

	public FunctionBean(ConfigBean configBean) {
		this.name = configBean.getName();
		this.configName = configBean.getConfigName();
		this.seekDetails = configBean.getSeekDetails();
		this.maxRetryCount = configBean.getMaxRetryCount();
		this.retryDelay = configBean.getRetryDelay();
	}
}