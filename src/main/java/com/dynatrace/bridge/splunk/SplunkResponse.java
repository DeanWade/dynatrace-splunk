package com.dynatrace.bridge.splunk;

public class SplunkResponse {
	
	private String text;
	
	private int code;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "SplunkResponse [text=" + text + ", code=" + code + "]";
	}
	
}
