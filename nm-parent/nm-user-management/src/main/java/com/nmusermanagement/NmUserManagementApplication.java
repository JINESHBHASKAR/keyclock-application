package com.nmusermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Jinesh KP
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.nmusermanagement"})
public class NmUserManagementApplication {
	
	public static void main(String[] args) {		
		SpringApplication.run(NmUserManagementApplication.class, args);
	}

}
