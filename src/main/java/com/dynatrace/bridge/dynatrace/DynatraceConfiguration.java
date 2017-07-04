package com.dynatrace.bridge.dynatrace;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class DynatraceConfiguration {

	@Bean("restTemplateForDynatrace")
	public RestTemplate restTemplateForSplunk(RestTemplateBuilder restTemplateBuilder) throws Exception {
		
	    RestTemplate restTemplate = restTemplateBuilder.build();
	    restTemplate.getInterceptors().add(new DynatraceAuthorizationInterceptor("xQOe3uCdStiLYsym3Mgnp"));
	    return restTemplate;
	}
}
