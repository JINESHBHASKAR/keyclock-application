package com.nmusermanagement.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nmusermanagement.service.UserProfileManagementService;
import com.nmusermanagement.util.KeyCloakTokenUtil;
import com.nmusermanagement.vo.CommonRequestVo;
import com.nmusermanagement.vo.CommonUserResponseVo;
import com.nmusermanagement.vo.UserProfileDetailsVo;
import com.nmusermanagement.vo.WardDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@Component
public class UserProfileManagementBusiness {
	private static final Logger log = LoggerFactory.getLogger(UserProfileManagementBusiness.class);

	@Autowired private UserProfileManagementService userProfileManagementService;	
	@Autowired private KeyCloakTokenUtil keyCloakTokenUtil;	
	
	public CommonUserResponseVo getWardInfo(String wardName) {
		log.info("inside getWardInfo method - UserProfileManagementBusiness");		
		CommonUserResponseVo response = userProfileManagementService.getWardInfo(wardName);
		response.setStatus("success");
		return response;
	}
	
	public CommonUserResponseVo userProfileUpdate(UserProfileDetailsVo request) {
		log.info("inside userProfileUpdate method - UserProfileManagementBusiness");
		CommonUserResponseVo response = new CommonUserResponseVo();		
		WardDetailsVo wardDetail = request.getWardDetail();
		if(StringUtils.isNotBlank(wardDetail.getWardName())) {
			request.setWardName(wardDetail.getWardName());
		}
		request.setProfileStatus(true);
		userProfileManagementService.userProfileUpdate(request);
		response.setStatus("success");
		return response;
	}
	
	public CommonUserResponseVo getMemberList() {
		log.info("inside userProfileUpdate method - UserProfileManagementBusiness");
		CommonRequestVo req = null;
		List<UserProfileDetailsVo> memberList = null;
		@SuppressWarnings("unchecked")
		List<String> roleList = (List<String>) keyCloakTokenUtil.getUserInfo("USER-ROLES");		
		String userId = (String) keyCloakTokenUtil.getUserInfo("USER-ID");
		req = new CommonRequestVo();		
		if(!roleList.isEmpty() && (!(roleList.contains("DISTRICT") || roleList.contains("MANDALAM"))) && (roleList.contains("PANCHAYATH") || roleList.contains("MUNICIPALITY"))) {
			//Get ward details						
			req.setUserId(userId);
			memberList = userProfileManagementService.getMemberList(req);
		}else if((roleList.contains("WARD-INCHARGE") && roleList.contains("MEMBER")) || 
					(roleList.size()==1 && roleList.contains("WARD-INCHARGE"))){
			req.setUserId(userId);
			req.setMemberRole("WARD-INCHARGE");
			memberList = userProfileManagementService.getMemberList(req);
		}else {
			memberList = userProfileManagementService.getMemberList(req);
		}		
		
		CommonUserResponseVo response = new CommonUserResponseVo();		
		response.setStatus("success");		
		response.setMemberList((memberList!=null && !memberList.isEmpty())?memberList:new ArrayList<>());		
		return response;
	}
		
}
