package com.dynatrace.bridge.splunk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BridgeCommandLineRunner implements CommandLineRunner{
	
//	private static final Logger log = LoggerFactory.getLogger(BridgeCommandLineRunner.class);
	
	@Autowired
	private BridgeConfiguration bridgeConfiguration;
	
	@Override
	public void run(String... args) throws Exception {
		
		BridgeConfiguration.HOST = bridgeConfiguration.getDtHost();
		BridgeConfiguration.INDEX = bridgeConfiguration.getIndex();
		BridgeConfiguration.SOURCE = bridgeConfiguration.getSource();
		BridgeConfiguration.SOURCETYPE = bridgeConfiguration.getSourcetype();
	}

}
