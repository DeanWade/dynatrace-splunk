package com.dynatrace.bridge.splunk;

import java.text.SimpleDateFormat;
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
	@Qualifier("restTemplateForAppMon")
	private RestTemplate restTemplateForAppMon;
	
	@Autowired
	@Qualifier("restTemplateForSplunk")
	private RestTemplate restTemplateForSplunk;
	
	public void bridgeAlertTask() {
		this.bridgeAlert(bridgeConfiguration.getInterval(), "m");
	}

    public void bridgeAlert(int last, String unit) {
    	
    	log.info("==================Task Start==================");
    	
    	int timmeUnit = Calendar.MINUTE;
    	if("h".equalsIgnoreCase(unit)){
    		timmeUnit = Calendar.HOUR_OF_DAY;
    	}else if("d".equalsIgnoreCase(unit)){
    		timmeUnit = Calendar.DAY_OF_MONTH;
    	}else if("m".equalsIgnoreCase(unit)){
    		timmeUnit = Calendar.MINUTE;
    	}
    	if(last <=0){
    		last = bridgeConfiguration.getInterval();
    	}
    	
    	Calendar calendar =Calendar.getInstance();
    	calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    	Date toDate = calendar.getTime(); 
    	String toStr = sdf.format(toDate);//"2017-06-01T11:35:31.170+08:00";
    	calendar.add(timmeUnit, 0-last);
    	Date fromDate = calendar.getTime();
    	String fromStr = sdf.format(fromDate);//"2017-07-01T11:35:31.170+08:00";
    	
    	log.info("==================From:" + fromStr);
    	log.info("==================To  :" + toStr);
    	
		DTResponse dtResponse = restTemplateForAppMon.getForObject(bridgeConfiguration.getUrlForAlert() + "?from={from}&to={to}", DTResponse.class, fromStr, toStr);
		List<DTAlert> alerts = dtResponse.getAlerts();
		for(DTAlert alert : alerts){
			DTAlertDetail alertDetail = restTemplateForAppMon.getForObject(bridgeConfiguration.getUrlForAlert() + "/{id}", DTAlertDetail.class,  alert.getId());
			alertDetail.setId( alert.getId());
			if(isRequiredSystemProfile(alertDetail.getSystemprofile())){
				SplunkRequest splunkRequest = SplunkRequest.newInstance(alertDetail);
				if(log.isDebugEnabled()){
					log.debug(splunkRequest.toString());
				}
				SplunkResponse splunkResponse = restTemplateForSplunk.postForObject(bridgeConfiguration.postUrlForSplunk(), splunkRequest, SplunkResponse.class);
				if(log.isDebugEnabled()){
					log.debug(splunkResponse.toString());
				}
			}
		}
		
		log.info("==================Task End===================");
    }
    
    private boolean isRequiredSystemProfile(String systemProfile){
    	String[] systemProfiles = bridgeConfiguration.getSystemprofiles();
    	if(systemProfiles == null || systemProfiles.length == 0){
    		return true;
    	}
    	for(String systemProfile0 : systemProfiles){
    		if(systemProfile0 != null && systemProfile0.equalsIgnoreCase(systemProfile)){
    			return true;
    		}
    	}
    	return false;
    }
}
