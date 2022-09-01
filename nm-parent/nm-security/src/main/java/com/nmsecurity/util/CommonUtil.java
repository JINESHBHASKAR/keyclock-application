package com.nmsecurity.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.nmsecurity.vo.UserDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@Component
public class CommonUtil {
	private final static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
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
	
	public void sendUserInfoViaSMS(UserDetailsVo request) {
		log.info("inside sendUserInfoViaSMS method - CommonUtil");
		
		try {
			//Creating message			
			StringBuilder str = new StringBuilder();
			str.append("Dear "+request.getFirstName().toUpperCase()+", Your Username : "+request.getUserName()+" Password : "+request.getPassword()+" successfully created");
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
	
	public String generate6DigitOtp(){
		log.info("inside generateOtp method : CommonUtil");
		Random random = new Random();
		//generate value between min value = 100000 max value = 999999 
		int min = 100000;
		int max = 999999;
		int num = (random.nextInt(max-min))+min;
		String otp =  String.valueOf(num);
		return otp;		
	}	
	
	
		
	/*public static void main(String[] args) {
		log.info(" = =================> "+ generate6DigitOtp());
	}*/
}
