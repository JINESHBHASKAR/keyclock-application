package com.nmsecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nmcommon.exception.BusinessException;
import com.nmsecurity.config.AppConfig;
import com.nmsecurity.mapper.UserDetailsMapper;
import com.nmsecurity.model.UserCredentials;
import com.nmsecurity.vo.UserDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserService {
	private final static Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired private UserDetailsMapper userDetailsMapper;
	
	@Autowired private AppConfig appConfig;
	
	public void updateUserInfo(UserDetailsVo request) {
		log.info("inside createUser method : UserService");
		try {
			//Update the user_details table 		
			Integer usrid = userDetailsMapper.updateUserInfo(request);
			log.info("user_details table updated {}",usrid);
						
			
			List<UserDetailsVo> roleList = new ArrayList<>();
			request.getRoles().stream().forEach(role -> {
				UserDetailsVo rle = new UserDetailsVo();
				rle.setUserId(request.getUserId());
				//get the role-key from config
				appConfig.getRoleList().forEach((key, value) -> {
					if(role.equals(value)) {
						rle.setRoleId(key);
					}					
				});
				roleList.add(rle);
			});
			Integer  r = userDetailsMapper.updateUserRoles(roleList);
			log.info("user_roles table updated,  {}",r);
			
			
			
		}catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	public void getUserProfileStatus(UserDetailsVo response) {
		log.info("inside createUser method : UserService");
		try {
			List<UserDetailsVo> userdetailList = userDetailsMapper.getUserProfileStatus(response);
			UserDetailsVo userdetail = userdetailList.get(0);
			if(userdetail!=null) {
				Boolean isProfileStatusUpdated = userdetail.getIsProfileStatusUpdated();
				if(isProfileStatusUpdated == null) {
					throw new BusinessException("User not avaiable", "ER_004");
				}
				response.setIsProfileStatusUpdated(isProfileStatusUpdated);
				if(StringUtils.isNotBlank(userdetail.getWardName())) {
					response.setWardName(userdetail.getWardName());
					response.setMandalam(StringUtils.isNotBlank(userdetail.getMandalam())?userdetail.getMandalam():"");
					response.setDistrict(StringUtils.isNotBlank(userdetail.getDistrict())?userdetail.getDistrict():"");
					response.setPostalCode(StringUtils.isNotBlank(userdetail.getPostalCode())?userdetail.getPostalCode():"");
					response.setArea(StringUtils.isNotBlank(userdetail.getArea())?userdetail.getArea():"");
					
				}
			}						
			
		}catch(Exception e) {
			log.error("Error reason :"+e.getMessage());
			if(e instanceof BusinessException) {				
				throw new BusinessException(e.getMessage(), "ER_004");								
			}else {
				throw new BusinessException(e.getMessage());
			}
			
		}
	}
	
	public String getUsernameByUserId(UserDetailsVo response) {
		log.info("inside getUsernameByUserId method : UserService");
		String userName = null;
		try {
			List<UserDetailsVo> userdetailList = userDetailsMapper.getUserProfileStatus(response);
			UserDetailsVo userdetail = userdetailList.get(0);
			userName = userdetail.getUserName();
		}catch(Exception e) {
			
		}
		return userName;
	}
	
	public void storeDeviceToken(UserCredentials userCredentials) {
		log.info("inside updateDeviceToken method : UserService");
		try {
			//Check the device token already existing
			UserCredentials userDeviceinfo = userDetailsMapper.getUserDeviceDetails(userCredentials.getDeviceToken());
			boolean isUpdateUserDeviceInfo = false;
			if(userDeviceinfo!=null) {
				log.info(" username : {} , DeviceToken : {} ",userDeviceinfo.getUsername(),userDeviceinfo.getDeviceToken());
				if(!userCredentials.getUsername().equals(userDeviceinfo.getUsername())) {
					isUpdateUserDeviceInfo = true;
				}
			}else {
				userCredentials.setStatus(true);
				userDetailsMapper.storeDeviceToken(userCredentials);
				log.info("User device token added successfully");
			}
			if(isUpdateUserDeviceInfo) {
				userDetailsMapper.updateUserDeviceInfo(userCredentials);
				log.info("username updated in userdevice info");
			}
			
		}catch(Exception e) {
			log.error("Error reason :"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
	
	public List<UserDetailsVo> getMemberListByWardId(Integer wardId){
		log.info("inside getMemberListByWardId method : UserService");
		List<UserDetailsVo> userdetailList = null;
		try {
			UserDetailsVo request = new UserDetailsVo();
			request.setWardId(wardId);
			userdetailList = userDetailsMapper.getUserProfileStatus(request);
		}catch(Exception e) {
			log.error("Error reason :"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return userdetailList;
	}
}
