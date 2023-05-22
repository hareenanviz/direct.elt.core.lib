package com.anvizent.elt.core.lib.config.bean;

import java.io.Serializable;

import com.anvizent.elt.core.lib.constant.Constants;

/**
 * @author Hareen Bejjanki
 * @author Apurva Deshmukh
 *
 */
public class SeekDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	private int at = -1;
	private int from = -1;
	private int to = -1;
	private String name = Constants.UNKNOWN;
	private String sourceName = Constants.UNKNOWN;

	public int getAt() {
		return at;
	}

	public void setAt(int at) {
		this.at = at;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public SeekDetails(int from, int to, String name, String sourceName) {
		this.from = from;
		this.to = to;
		this.name = name;
		this.sourceName = sourceName;
	}

	public SeekDetails(int at, String name, String sourceName) {
		this.at = at;
		this.name = name;
		this.sourceName = sourceName;
	}

	public SeekDetails(String name, String sourceName) {
		this.name = name;
		this.sourceName = sourceName;
	}

	@Override
	public String toString() {
		String name = this.name.replaceFirst("at ", "");

		if (at != -1) {
			return " in the source '" + sourceName + "' at " + name + at;
		} else {
			return " in the source '" + sourceName + "' from " + name + from + ", to " + name + to;
		}
	}
}