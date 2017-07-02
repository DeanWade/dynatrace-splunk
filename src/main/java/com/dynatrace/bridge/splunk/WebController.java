package com.dynatrace.bridge.splunk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@Autowired
	private BridgeWoker bridgeWoker;
	
	@GetMapping("/api/v1/alerts")
	public void bridgeAlert(){
		bridgeWoker.bridgeAlert();;
	}
}
