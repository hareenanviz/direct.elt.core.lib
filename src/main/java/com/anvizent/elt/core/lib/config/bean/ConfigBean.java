package com.anvizent.elt.core.lib.config.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class ConfigBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private ArrayList<String> sources;
	private String sourceStream;
	private boolean persist;
	private boolean multiOut;
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

	public String getSource() {
		return sources != null && sources.size() == 1 ? sources.get(0) : null;
	}

	public ArrayList<String> getSources() {
		return sources;
	}

	public void setSources(ArrayList<String> sources) {
		this.sources = sources;
	}

	public String getSourceStream() {
		return sourceStream;
	}

	public void setSourceStream(String sourceStream) {
		this.sourceStream = sourceStream;
	}

	public boolean isPersist() {
		return persist;
	}

	public void setPersist(boolean persist) {
		this.persist = persist;
	}

	public boolean isMultiOut() {
		return multiOut;
	}

	public void setMultiOut(boolean multiOut) {
		this.multiOut = multiOut;
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
}