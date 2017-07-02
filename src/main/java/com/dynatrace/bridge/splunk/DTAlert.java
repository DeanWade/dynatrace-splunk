package com.dynatrace.bridge.splunk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTAlert {

	private String id;
	private String href;
	private String rule;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	@Override
	public String toString() {
		return "DTAlert [id=" + id + ", href=" + href + ", rule=" + rule + "]";
	}
	
}
