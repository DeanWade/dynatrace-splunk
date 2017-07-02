package com.dynatrace.bridge.splunk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTAlertDetail {

	private String id;
	private String severity;
	private String state;
	private String message;
	private String start;
	private String end;
	private String rule;
	private String systemprofile;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getSystemprofile() {
		return systemprofile;
	}
	public void setSystemprofile(String systemprofile) {
		this.systemprofile = systemprofile;
	}
	@Override
	public String toString() {
		return "DTAlertDetail [id=" + id + ", severity=" + severity + ", state=" + state + ", message=" + message
				+ ", start=" + start + ", end=" + end + ", rule=" + rule + ", systemprofile=" + systemprofile + "]";
	}
	
}
