package com.dynatrace.bridge.splunk;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

public class SplunkAuthorizationInterceptor implements ClientHttpRequestInterceptor {
	
	private final String token;
	
	public SplunkAuthorizationInterceptor(String token) {
		Assert.hasLength(token, "token must not be empty");
		this.token = token;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		request.getHeaders().add("Authorization", "Splunk " + token);
		return execution.execute(request, body);
	}

}
