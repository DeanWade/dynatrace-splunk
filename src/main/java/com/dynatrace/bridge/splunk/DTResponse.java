package com.dynatrace.bridge.splunk;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTResponse {
	
	private List<DTAlert> alerts;

	public List<DTAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<DTAlert> alerts) {
		this.alerts = alerts;
	}
	
	@Override
	public String toString() {
		return "DTResponse [alerts=" + getAlerts().toString() + "]";
	}
	
}
