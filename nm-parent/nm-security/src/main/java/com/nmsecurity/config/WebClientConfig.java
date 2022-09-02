package com.nmsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 
 * @author Jinesh KP
 *
 */

@Configuration
public class WebClientConfig {
	private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);
	
	@Value("${sms-api.base-uri}")	
	private String smsApiBaseUri;	
	
	@Bean(name = "webClientSMS")
	public WebClient webClient() {
		log.info("inside webClient method - WebClientConfig");
		WebClient client = WebClient.create(smsApiBaseUri);
		return client;
	}
}
