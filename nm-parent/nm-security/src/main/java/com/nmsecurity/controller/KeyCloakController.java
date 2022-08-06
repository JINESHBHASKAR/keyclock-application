package com.nmsecurity.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nmsecurity.business.KeyCloakBusiness;
import com.nmsecurity.model.UserCredentials;
import com.nmsecurity.vo.ConfigVo;
import com.nmsecurity.vo.ResponseVo;
import com.nmsecurity.vo.UserDetailsVo;

@RestController
@RequestMapping("/nm")
public class KeyCloakController {

	private static final Logger log = LoggerFactory.getLogger(KeyCloakController.class);
	
	@Autowired private KeyCloakBusiness keyCloakBusiness;
	
	
	
	@GetMapping (value = "/config")
	public ConfigVo getConfigDetails(HttpServletRequest request) {
		log.info("inside getConfigDetails method - KeyCloakController ");
		ConfigVo response = new ConfigVo();
		keyCloakBusiness.configResponse(response,request);		
		keyCloakBusiness.getWardNameAndId(response);
		return response;
	}
	
	/*
	 * Get token for the first time when user log in. We need to pass
	 * credentials only once. Later communication will be done by sending token.
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseVo getTokenUsingCredentials(@RequestBody UserCredentials userCredentials,HttpServletRequest request) {
		log.info("inside getTokenUsingCredentials method - KeyCloakController ");
		ResponseVo response = null;		
		String deviceToken = request.getHeader("DEVICE_TOKEN");
		log.info("User name: {}, Device Token : {}",userCredentials.getUsername(),deviceToken);
		if(StringUtils.isNotBlank(deviceToken)) {
			userCredentials.setDeviceToken(deviceToken);
		}
		response = keyCloakBusiness.getToken(userCredentials);	
		return response;

	}
	
	@GetMapping(path = "/logout")
	public ResponseVo logout() throws ServletException {
		log.info("inside logout method - KeyCloakController ");				
	    return keyCloakBusiness.logout();
	}
	
	@GetMapping(path = "/get-new-access-token")
	public ResponseVo getTokenUsingRefreshToken(@RequestHeader(value = "REFRESH_TOKEN") String refreshToken) {
		log.info("inside getTokenUsingRefreshToken method - KeyCloakController ");		
		return keyCloakBusiness.getTokenUsingRefreshToken(refreshToken);		
	}
	
	@GetMapping(path = "/user-details")
	public UserDetailsVo getUserDetails() {
		log.info("inside getUserDetails method - KeyCloakController ");				     
		return keyCloakBusiness.getUserDetails();
	}
	
	@GetMapping(value = {"/check-username-availability","/check-mobile-number-availability"})
	public ResponseVo isUserAttributesAvailability(@RequestParam(name = "userName", required = false) String userName,@RequestParam(name = "mobile", required = false) String mobile) {
		log.info("inside isUserAttributesAvailability method - KeyCloakController ");			 
		UserDetailsVo request = new UserDetailsVo();
		if(StringUtils.isNotBlank(userName)) {
			request.setUserName(userName);
		}else {
			request.setMobile(mobile);
		}
		return keyCloakBusiness.isUserAttributesAvailability(request);
	}
	
	@GetMapping(value = "/get-member-by-ward")
	public ResponseVo getMemberListByWardId(@RequestParam(value = "wardId")Integer wardId) {
		log.info("inside getMemberListByWardId method - KeyCloakController ");		
		return keyCloakBusiness.getMemberListByWardId(wardId);
	}
	
	@PostMapping(path = "/create-user")
	public UserDetailsVo createUser(@RequestBody UserDetailsVo request) {
		log.info("inside createUser method - KeyCloakController ");
		return keyCloakBusiness.createUser(request);
	}
	
	@PostMapping(path = "/reset-password")
	public ResponseVo resetPassword(@RequestBody UserDetailsVo request) {
		log.info("inside resetPassword method - KeyCloakController ");		
		return keyCloakBusiness.resetPassword(request);
	}
}


