package com.nmmembercommunication;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NmMemberCommunicationApplication {
	private static final Logger log = LoggerFactory.getLogger(NmMemberCommunicationApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(NmMemberCommunicationApplication.class, args);
	}
	@PostConstruct
    public void init(){
	  log.info(" Setting NmMemberCommunicationApplication SetTimeZone : {}",TimeZone.getTimeZone("IST"));
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    }
}
