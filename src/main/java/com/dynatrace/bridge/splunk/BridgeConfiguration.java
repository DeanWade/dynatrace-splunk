package com.dynatrace.bridge.splunk;

import java.util.Arrays;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BridgeConfiguration {
	
	//dynatrace appmon properties
	@Value("${dynatrace.schema}")
	private String dtSchema = "http";
	
	@Value("${dynatrace.host}")
	private String dtHost = "localhost";
	
	@Value("${dynatrace.port}")
	private long dtPort = 8020;
	
	@Value("${dynatrace.username}")
	private String username = "admin";
	
	@Value("${dynatrace.password}")
	private String password = "admin";
	
	@Value("${dynatrace.systemprofiles}")
	private String[] systemprofiles = null;

	@Value("${dynatrace.interval}")
	private int interval = 5;
	
	//splunk properties
	@Value("${splunk.schema}")
	private String splunkSchema = "http";
	
	@Value("${splunk.host}")
	private String splunkHost = "";
	
	public static String HOST = "";
	
	@Value("${splunk.port}")
	private long splunkPort = 8088;
	
	@Value("${splunk.token}")
	private String token = "";
	
	@Value("${splunk.index}")
	private String index = "";
	
	public static String INDEX = "";
	
	@Value("${splunk.source}")
	private String source = "";
	
	public static String SOURCE = "";
	
	
	@Value("${splunk.sourcetype}")
	private String sourcetype = "_json";
	
	public static String SOURCETYPE = "_json";
	
	public String postUrlForSplunk(){
		return splunkSchema + "://" + splunkHost + ":" + splunkPort + "/services/collector/event";
	}
	
	public String getUrlForAlert(){
		return dtSchema + "://" + dtHost + ":" + dtPort + "/api/v1/alerts";
	}

	@Bean("restTemplateForDynatrace")
	public RestTemplate restTemplateForDynatrace(RestTemplateBuilder restTemplateBuilder) throws Exception {
		
//	  	TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//	                    .loadTrustMaterial(null, acceptingTrustStrategy)
//	                    .build();
//
//	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		
//	   	SSLContextBuilder builder = new SSLContextBuilder();
//	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
//		    
//		CloseableHttpClient httpclient = HttpClients
//				.custom()
////				.setSSLSocketFactory(sslsf)
//				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
//				.build();
//
//	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//
//	    requestFactory.setHttpClient(httpclient);
////	    RestTemplate restTemplate = new RestTemplate(requestFactory);
////	    return restTemplate;
//		
//	    restTemplateBuilder.requestFactory(requestFactory);
//	    restTemplateBuilder.basicAuthorization("admin", "admin");
	    RestTemplate restTemplate = restTemplateBuilder.build();
	    restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));
	    return restTemplate;
	}
	
	@Bean("restTemplateForSplunk")
	public RestTemplate restTemplateForSplunk(RestTemplateBuilder restTemplateBuilder) throws Exception {
		
	    RestTemplate restTemplate = restTemplateBuilder.build();
	    restTemplate.getInterceptors().add(new SplunkAuthorizationInterceptor(token));
	    return restTemplate;
	}
	
	@Bean(name = "scheduledTask")
	public MethodInvokingJobDetailFactoryBean scheduledTasks(BridgeWoker dataBridgeWoker) {
		MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
		bean.setTargetObject(dataBridgeWoker);
		bean.setTargetMethod("bridgeAlert");
		bean.setConcurrent(false);
		return bean;
	}

	@Bean(name = "cronTriggerBean")
	public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetail jobDetails) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetails);
		tigger.setCronExpression("0 0/" + interval + " * * * ?");
		return tigger;

	}

	@Bean(name = "schedulerFactory")
	public SchedulerFactoryBean schedulerFactory(CronTrigger[] cronTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setTriggers(cronTrigger);
		return bean;
	}

	public String getDtSchema() {
		return dtSchema;
	}

	public void setDtSchema(String dtSchema) {
		this.dtSchema = dtSchema;
	}

	public String getDtHost() {
		return dtHost;
	}

	public void setDtHost(String dtHost) {
		this.dtHost = dtHost;
		BridgeConfiguration.HOST = dtHost;
	}

	public long getDtPort() {
		return dtPort;
	}

	public void setDtPort(long dtPort) {
		this.dtPort = dtPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getSystemprofiles() {
		return systemprofiles;
	}

	public void setSystemprofiles(String[] systemprofiles) {
		this.systemprofiles = systemprofiles;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getSplunkSchema() {
		return splunkSchema;
	}

	public void setSplunkSchema(String splunkSchema) {
		this.splunkSchema = splunkSchema;
	}

	public String getSplunkHost() {
		return splunkHost;
	}

	public void setSplunkHost(String splunkHost) {
		this.splunkHost = splunkHost;
	}

	public long getSplunkPort() {
		return splunkPort;
	}

	public void setSplunkPort(long splunkPort) {
		this.splunkPort = splunkPort;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
		BridgeConfiguration.INDEX = index;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
		BridgeConfiguration.SOURCE = source;
	}

	public String getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}

	@Override
	public String toString() {
		return "BridgeConfiguration [dtSchema=" + dtSchema + ", dtHost=" + dtHost + ", dtPort=" + dtPort + ", username="
				+ username + ", password=" + password + ", systemprofiles=" + Arrays.toString(systemprofiles)
				+ ", interval=" + interval + ", splunkSchema=" + splunkSchema + ", splunkHost=" + splunkHost
				+ ", splunkPort=" + splunkPort + ", token=" + token + ", index=" + index + ", source=" + source
				+ ", sourcetype=" + sourcetype + "]";
	}
	
}
