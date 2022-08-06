package com.nmsecurity.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.nmcommon.exception.BusinessException;
import com.nmsecurity.config.AppConfig;
import com.nmsecurity.model.UserCredentials;
import com.nmsecurity.service.KeyCloakService;
import com.nmsecurity.service.UserService;
import com.nmsecurity.util.CommonUtil;
import com.nmsecurity.util.KeyCloakUtil;
import com.nmsecurity.vo.ConfigVo;
import com.nmsecurity.vo.ResponseVo;
import com.nmsecurity.vo.UserDetailsVo;

@Component
public class KeyCloakBusiness {
	private static final Logger log = LoggerFactory.getLogger(KeyCloakBusiness.class);

	@Autowired private KeyCloakService keyCloakService;
	@Autowired private KeyCloakUtil keyCloakUtil;
	@Autowired private UserService userService;
	@Autowired private AppConfig appConfig;
	@Autowired private CommonUtil commonUtil;
	@Value("${config.key}")	
	private String appKey;
	
	@Value("${config.app-version}")	
	private String appVersion;
	
	@Value("${config.app-name}")	
	private String appName;
	
	public ResponseVo getToken(UserCredentials userCredentials) {
		log.info("inside getToken method - KeyCloakBusiness ");
		ResponseVo res = keyCloakService.getToken(userCredentials);		
		if(StringUtils.isNotBlank(userCredentials.getDeviceToken()) && res.getStatus().equals("success")) {
			userService.storeDeviceToken(userCredentials);
		}else {
			throw new BusinessException("invalid username or password");
		}
		return res;
	}

	public UserDetailsVo getUserDetails() {
		log.info("inside getUserDetails method - KeyCloakBusiness ");
		UserDetailsVo response = null;
		try {
			response = keyCloakUtil.getUserInfo("USER-DETAILS");
			userService.getUserProfileStatus(response);
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				if (StringUtils.isNotBlank(((BusinessException) e).getErrorCode())) {
					throw new BusinessException(e.getMessage(), ((BusinessException) e).getErrorCode());
				} else {
					throw new BusinessException(e.getMessage());
				}
			}
			throw new BusinessException(e.getMessage());
		}

		response.setStatus("SUCCESS");
		return response;
	}

	public UserDetailsVo createUser(UserDetailsVo request) {
		log.info("inside createUser method - KeyCloakBusiness ");
		String password = request.getPassword();
		keyCloakService.createUser(request);		
		UserDetailsVo response = new UserDetailsVo();
		response.setStatus("success");
		if(request.getEnableSms()) {
			request.setPassword(password);
			commonUtil.sendUserInfoViaSMS(request);
		}
		return response;
	}

	public ResponseVo isUserAttributesAvailability(UserDetailsVo request) {
		log.info("inside isUserAttributesAvailability method - KeyCloakBusiness ");
		ResponseVo response = new ResponseVo();
		Boolean isUserAttributeAvailable = keyCloakService.isUserAttributesAvailability(request);
		response.setStatus("success");
		if (StringUtils.isNotBlank(request.getMobile())) {
			response.setIsMobileNumberAvailable(isUserAttributeAvailable);
		} else if (StringUtils.isNotBlank(request.getUserName())) {
			response.setIsUserNameAvailable(isUserAttributeAvailable);
		}

		return response;
	}

	public ResponseVo getMemberListByWardId(Integer wardId) {
		log.info("inside getMemberListByWardId method - KeyCloakBusiness ");
		List<UserDetailsVo> memberByWardIdList = userService.getMemberListByWardId(wardId);
		ResponseVo response = new ResponseVo();
		if(memberByWardIdList!=null) {
			response.setMemberList(memberByWardIdList);			
		}else {
			response.setMemberList(new ArrayList<>());
		}		
		response.setStatus("success");
		return response;
	}
	public ResponseVo logout() {
		log.info("inside logout method - KeyCloakBusiness ");
		ResponseVo response = new ResponseVo();
		keyCloakService.logout();
		response.setStatus("success");
		return response;

	}

	public ResponseVo getTokenUsingRefreshToken(String refreshToken) {
		log.info("inside logout method - KeyCloakBusiness ");		
		ResponseVo response = keyCloakService.getTokenUsingRefreshToken(refreshToken);		
		return response;
	}
	
	public ConfigVo getWardNameAndId(ConfigVo response) {
		log.info("inside getWardNameAndId method - KeyCloakBusiness ");
		ConfigVo res = keyCloakService.getWardNameAndId();
		response.setWardNameMap(res.getWardNameMap());
		return response;
	}
	
	public void configResponse(ConfigVo response,HttpServletRequest request) {
		log.info("inside configResponse method - KeyCloakBusiness ");
		if(appName.equals(request.getHeader("APP-NAME")) &&  appKey.equals(request.getHeader("CONFIG-KEY"))) {
			response.setStatus("success");
			response.setErrorCode(appConfig.getErrorCode());
			response.setContentMessage(appConfig.getContentMessage());
			response.setCashTransaction(appConfig.getCashTransaction());
			response.setDistrictNames(appConfig.getDistrict());
			response.setStateNames(appConfig.getState());
		}else {
			throw new BusinessException("UnAuthorized user");
		}
		if(!appVersion.equals(request.getHeader("VERSION"))) {
			response.setForceUpdate(true);
		}else {
			response.setForceUpdate(false);
		}
		
	}
	
	public ResponseVo resetPassword(@RequestBody UserDetailsVo request) {
		log.info("inside resetPassword method - KeyCloakBusiness ");		
		UserDetailsVo res = null;
		res = keyCloakUtil.getUserInfo("USER-ID");
		if(StringUtils.isNotBlank(res.getUserId())) {
			request.setUserId(res.getUserId());
			keyCloakService.resetPassword(request);
		}
		ResponseVo response = new ResponseVo();
		
		response.setStatus("success");
		return response;
	}
}
