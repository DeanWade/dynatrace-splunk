package com.dynatrace.bridge.splunk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BridgeWoker {

    private static final Logger log = LoggerFactory.getLogger(BridgeWoker.class);
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
    @Autowired
    private BridgeConfiguration bridgeConfiguration;
    
	@Autowired
	@Qualifier("restTemplateForDynatrace")
	private RestTemplate restTemplateForDynatrace;
	
	@Autowired
	@Qualifier("restTemplateForSplunk")
	private RestTemplate restTemplateForSplunk;

    public void bridgeAlert() {
    	
    	log.info("==================Task Start==================");
    	
    	Date toDate = new Date(); 
    	String toStr = sdf.format(toDate);//"2017-06-01T11:35:31.170+08:00";
    	Calendar calendar =Calendar.getInstance();
    	calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    	calendar.setTime(toDate);
    	calendar.add(Calendar.MINUTE, -bridgeConfiguration.getInterval());
    	Date fromDate = calendar.getTime();
    	String fromStr = sdf.format(fromDate);//"2017-07-01T11:35:31.170+08:00";
    	log.info("==================" + fromStr);
    	log.info("==================" + toStr);
    	
		DTResponse dtResponse = restTemplateForDynatrace.getForObject(
				bridgeConfiguration.getUrlForAlert() + "?from={from}&to={to}", DTResponse.class, fromStr, toStr);
		
		List<DTAlert> alerts = dtResponse.getAlerts();
		List<DTAlertDetail> alertDetails = new ArrayList<DTAlertDetail>(alerts.size());
		for(DTAlert alert : alerts){
			String alertId = alert.getId();
			DTAlertDetail alertDetail = restTemplateForDynatrace.getForObject(
					bridgeConfiguration.getUrlForAlert() + "/{id}", DTAlertDetail.class, alertId);
			alertDetail.setId(alertId);
			if(isRequiredSystemProfile(alertDetail.getSystemprofile())){
				alertDetails.add(alertDetail);
			}
		}
		if(!alertDetails.isEmpty()){
			SplunkRequest splunkRequest = SplunkRequest.newInstance();
			splunkRequest.setEvent(alertDetails);
			SplunkResponse splunkResponse = restTemplateForSplunk.postForObject(
					bridgeConfiguration.postUrlForSplunk(), splunkRequest, SplunkResponse.class);
			log.info(splunkResponse.toString());
		}else{
			log.info("no alert detected");
		}
		
		log.info("==================Task End===================");
    }
    
    private boolean isRequiredSystemProfile(String systemProfile){
    	String[] systemProfiles = bridgeConfiguration.getSystemprofiles();
    	if(systemProfiles == null){
    		return true;
    	}
    	for(String systemProfile0 : systemProfiles){
    		if(systemProfile0 != null && systemProfile0.equals(systemProfile)){
    			return true;
    		}
    	}
    	return false;
    }
}
