package com.dynatrace.bridge.splunk;

public class SplunkRequest {

	private long time;
	private String host;
	private String source;
	private String sourcetype;
	private String index;
	private Object event;
	
	public static SplunkRequest newInstance(){
		SplunkRequest request = new SplunkRequest();
		request.time = System.currentTimeMillis();
		request.host = BridgeConfiguration.HOST;
		request.source = BridgeConfiguration.SOURCE;
		request.sourcetype = BridgeConfiguration.SOURCETYPE;
		request.index = BridgeConfiguration.INDEX;
		return request;
	}
	
	public static SplunkRequest newInstance(DTAlertDetail alertDetail){
		SplunkRequest request = SplunkRequest.newInstance();
		request.event = alertDetail;
		return request;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourcetype() {
		return sourcetype;
	}
	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public Object getEvent() {
		return event;
	}
	public void setEvent(Object event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "SplunkRequest [time=" + time + ", host=" + host + ", source=" + source + ", sourcetype=" + sourcetype
				+ ", index=" + index + ", event=" + event + "]";
	}
	
}
