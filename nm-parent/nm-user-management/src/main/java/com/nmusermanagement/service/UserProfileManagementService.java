package com.nmusermanagement.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nmcommon.exception.BusinessException;
import com.nmusermanagement.mapper.UserProfileManagementMapper;
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

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserProfileManagementService {
	private static final Logger log = LoggerFactory.getLogger(UserProfileManagementService.class);
	
	@Autowired private UserProfileManagementMapper userProfileManagementMapper;
	@Autowired private KeyCloakTokenUtil keyCloakTokenUtil;
	
	
	public CommonUserResponseVo getWardInfo(String wardName) {
		log.info("inside getWardInfo method - UserProfileManagementService");
		CommonUserResponseVo response = new CommonUserResponseVo();
		try {
			WardDetailsVo wardDetails = userProfileManagementMapper.getWardDetails(wardName);
			if(wardDetails != null)
				response.setWardDetails(wardDetails);
			else
				throw new BusinessException("Invalid ward name");
		}catch(Exception e) {
			log.error(" error reason : "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}		
		return response;
	}
	
	public void userProfileUpdate(UserProfileDetailsVo request) {
		log.info("inside getWardInfo method - UserProfileManagementService");
		try {
			String userId = (String) keyCloakTokenUtil.getUserInfo("USER-ID"); 
			request.setUserId(userId);
			userProfileManagementMapper.userProfileUpdate(request);
		}catch(Exception e) {
			log.error(" error reason : "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
	
	public List<UserProfileDetailsVo> getMemberList(CommonRequestVo req) {
		log.info("inside getMemberList method - UserProfileManagementService");
		List<UserProfileDetailsVo> memberList = null;
		try {
			if(req!=null && StringUtils.isNotBlank(req.getUserId())) {
				UserProfileDetailsVo userDetails = userProfileManagementMapper.getUserDetails(req);
				if(StringUtils.isNotBlank(userDetails.getWardName())) {
					req.setWardName(userDetails.getWardName());
					if(StringUtils.isNotBlank(req.getMemberRole()) && req.getMemberRole().equals("WARD-INCHARGE")) {
						memberList = userProfileManagementMapper.getMemberList(Arrays.asList(userDetails.getWardName()));
					}else {
						List<WardDetailsVo> wardList = userProfileManagementMapper.getWardList(req);
						if(!wardList.isEmpty()) {
							List<String> wardNameList = new ArrayList<>();
							wardList.stream().forEach(ward -> {
								wardNameList.add(ward.getWardName());
							});						
							if(wardNameList!=null && !wardNameList.isEmpty()) {
								memberList = userProfileManagementMapper.getMemberList(wardNameList);
							}
						}
					}													
				}
			}else {
				memberList = userProfileManagementMapper.getMemberList(null);
			}			
		}catch(Exception e) {
			log.error(" error reason : "+e.getMessage());
			throw new BusinessException(e.getMessage());					
		}
		return memberList;
	}
}
