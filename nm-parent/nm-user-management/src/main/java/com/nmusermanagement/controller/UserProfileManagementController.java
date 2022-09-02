package com.nmusermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nmusermanagement.business.UserProfileManagementBusiness;
import com.nmusermanagement.vo.CommonUserResponseVo;
import com.nmusermanagement.vo.UserProfileDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@RestController
@RequestMapping(path = "/user-management")
public class UserProfileManagementController {
	private static final Logger log = LoggerFactory.getLogger(UserProfileManagementController.class);
	
	@Autowired private UserProfileManagementBusiness userProfileManagementBusiness;
	
	@GetMapping(value = "/get-ward-info")
	public CommonUserResponseVo getWardInfo(@RequestParam String wardName) {
		log.info("inside getWardInfo method - UserProfileManagementController");		
		return userProfileManagementBusiness.getWardInfo(wardName);
	}
	
	@PostMapping(value = "/update-profile")
	public CommonUserResponseVo userProfileUpdate(@RequestBody UserProfileDetailsVo request) {
		log.info("inside userProfileUpdate method - UserProfileManagementController");
		return userProfileManagementBusiness.userProfileUpdate(request);
	}
	
	@GetMapping(value = "/get-member-list")
	public CommonUserResponseVo getMemberList() {
		log.info("inside getMemberList method - UserProfileManagementController");
		return userProfileManagementBusiness.getMemberList();
	}
}
