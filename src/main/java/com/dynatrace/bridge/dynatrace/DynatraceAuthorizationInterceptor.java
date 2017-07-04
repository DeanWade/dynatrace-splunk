package com.dynatrace.bridge.dynatrace;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

public class DynatraceAuthorizationInterceptor implements ClientHttpRequestInterceptor {
	
	private final String apiToken;
	
	public DynatraceAuthorizationInterceptor(String apiToken) {
		Assert.hasLength(apiToken, "apiToken must not be empty");
		this.apiToken = apiToken;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		request.getHeaders().add("Authorization", "Api‐Token " + apiToken);
		return execution.execute(request, body);
	}

}
