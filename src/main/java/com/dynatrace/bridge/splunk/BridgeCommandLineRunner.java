package com.dynatrace.bridge.splunk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//@Component
public class BridgeCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	private BridgeWoker bridgeWoker;
	
	@Override
	public void run(String... args) throws Exception {
		bridgeWoker.bridgeAlert();
	}

}
