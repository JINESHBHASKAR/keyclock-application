package com.nmcampaignsmaintenance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.nmcampaignsmaintenance.ValueObject.RequestVo;

@Component
public class CommonUtil {
	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	@Value("${sms-api.api-key}")	
	private String smsApiKey;
	
	@Value("${sms-api.type}")	
	private String smsApiType;
	
	@Value("${sms-api.sender-id}")	
	private String smsApiSenderId;
	
	@Value("${sms-api.flash}")	
	private String flashValue;
	
	@Autowired
	@Qualifier("webClientSMS")
	private WebClient webClient;
	
	
	public void sendReceiptInfoViaSMS(RequestVo request) {
		log.info("inside sendReceiptInfoViaSMS method - CommonUtil");
		
		try {
			//Creating message			
			StringBuilder str = new StringBuilder();
			str.append("Dear "+request.getFundDonorName()+", thank you for donation. Your Receipt No: "+request.getReceiptId()+" fund amount : Rs."+request.getAmount());
			log.info("Message "+str);
			//Creating request
			@SuppressWarnings("rawtypes")
			WebClient.RequestHeadersSpec requestSpec = webClient.get().uri(uriBuilder -> uriBuilder.queryParam("key", smsApiKey)
														.queryParam("type", smsApiType)
														.queryParam("to", request.getMobile())
														.queryParam("sender", smsApiSenderId)
														.queryParam("message", str)
														.queryParam("flash", flashValue)
														.build());
			
			//Getting a Response
			String response = requestSpec.retrieve().bodyToMono(String.class).block();
			log.info("response = "+response);
		}catch(Exception e) {
			log.error("error reason : "+e.getMessage());
			if(e instanceof WebClientException) {
				
			}			
		}
		
	}
	
}
