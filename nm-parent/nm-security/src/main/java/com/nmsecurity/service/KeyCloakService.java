package com.nmsecurity.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmcommon.constants.AppConstants;
import com.nmcommon.exception.BusinessException;
import com.nmsecurity.mapper.UserDetailsMapper;
import com.nmsecurity.model.UserCredentials;
import com.nmsecurity.util.KeyCloakUtil;
import com.nmsecurity.vo.ConfigVo;
import com.nmsecurity.vo.ResponseVo;
import com.nmsecurity.vo.UserDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class KeyCloakService {
	private static final Logger log = LoggerFactory.getLogger(KeyCloakService.class);

	@Value("${keycloak.resource}")
	private String CLIENT_ID;

	@Value("${keycloak.credentials.secret}")
	private String CLIENT_SECRET;

	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;

	@Value("${keycloak.realm}")
	private String REALM;

	@Autowired 
	@Qualifier("keyCloakInstanceForUserCreation")
	private Keycloak keyCloakInstanceForUserCreation;
	
	@Autowired 
	@Qualifier("keyCloakInstanceForTokenCreation")
	private Keycloak keyCloakInstanceForTokenCreation;

	@Autowired private KeyCloakUtil keyCloakUtil;
	
	@Autowired private UserDetailsMapper userDetailsMapper;
	
	@Autowired private UserService userService;	
	
	public ResponseVo getToken(UserCredentials userCredentials) {
		log.info("inside getToken method - KeyCloakService");
		String responseToken = null;
		ResponseVo response = null;
		try {

			String username = userCredentials.getUsername();

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("grant_type", AppConstants.GRANT_TYPE));
			urlParameters.add(new BasicNameValuePair("client_id", CLIENT_ID));
			urlParameters.add(new BasicNameValuePair("username", username));
			urlParameters.add(new BasicNameValuePair("password", userCredentials.getPassword()));
			urlParameters.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));

			responseToken = sendPost(urlParameters);

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(responseToken, ResponseVo.class);
			if (StringUtils.isNotBlank(response.getError())) {
				response.setStatus("failed");
			} else {
				response.setStatus("success");
			}
		} catch (Exception e) {
			log.error("Error reason: " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return response;
	}

	private String sendPost(List<NameValuePair> urlParameters) throws Exception {
		log.info("inside sendPost method KeyCloakService " + urlParameters);

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(AUTHURL + "/realms/" + REALM + "/protocol/openid-connect/token");
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();

	}

	// after logout user from the keycloak system. No new access token will be
	// issued.
	public void logoutUser(String userId) {

		UsersResource userRessource = getKeycloakUserResource();

		userRessource.get(userId).logout();

	}

	private UsersResource getKeycloakUserResource() {

		//Keycloak kc = keyCloakUtil.getKeyCloakInstanceForTokenCreation();

		RealmResource realmResource = keyCloakInstanceForTokenCreation.realm(REALM);
		UsersResource userRessource = realmResource.users();

		return userRessource;
	}

	public Boolean isUserAttributesAvailability(UserDetailsVo request) {
		log.info("inside isUserNameAvailable method KeyCloakService ");
		UsersResource usersResource = null;
		Boolean isUserAttributeAvailable = false;
		try {			
			RealmResource realmResource = keyCloakInstanceForUserCreation.realm(REALM);
			usersResource = realmResource.users();
			if(StringUtils.isNotBlank(request.getUserName())) {
				List<UserRepresentation> userRepresentationList = usersResource.search(request.getUserName());
				// Check the availability of the username			
				isUserAttributeAvailable = userRepresentationList.stream().anyMatch(new Predicate<UserRepresentation>() {
					public boolean test(UserRepresentation t) {					
						return t.getUsername().equals(request.getUserName());
					}
				});
			}else if(StringUtils.isNotBlank(request.getMobile())) {
				List<UserRepresentation> userRepresentationList = usersResource.search("");
				// Check the availability of the mobile			
				isUserAttributeAvailable = userRepresentationList.stream().anyMatch(new Predicate<UserRepresentation>() {
					public boolean test(UserRepresentation userRepresentation) {						
						boolean isMobileNumberAvailable = false;
						if(StringUtils.isNotBlank(userRepresentation.getAttributes().get("mobile").get(0))) {
							isMobileNumberAvailable =  userRepresentation.getAttributes().get("mobile").get(0).equals(request.getMobile());													
						}	
						return isMobileNumberAvailable;
					}
				});
			}					
		} catch (Exception e) {
			log.error("Error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return isUserAttributeAvailable;
	}

	public void createUser(UserDetailsVo request) {
		log.info("inside createUser method KeyCloakService ");
		UsersResource usersResource = null;		
		try {			
			RealmResource realmResource = keyCloakInstanceForUserCreation.realm(REALM);
			usersResource = realmResource.users();
			// Create new user
			UserRepresentation user = new UserRepresentation();
			user.setUsername(request.getUserName());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			Map<String, List<String>> attributes = new HashMap<>();
			attributes.put("mobile", Arrays.asList(request.getMobile()));
			user.setAttributes(attributes);
			user.setEnabled(true);

			Response result = usersResource.create(user);
			log.info("User created status" + result.getStatus());
			if (result.getStatus() == HttpStatus.SC_CREATED) {
				// Define password credential
				String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
				CredentialRepresentation passwordCred = new CredentialRepresentation();
				passwordCred.setTemporary(false);
				passwordCred.setType(CredentialRepresentation.PASSWORD);
				passwordCred.setValue(request.getPassword());
				UserResource userResource = usersResource.get(userId);
				userResource.resetPassword(passwordCred);
				log.info("Password created successfully");
				// Set roles to the user
				List<RoleRepresentation> roleList = new ArrayList<>();
				request.getRoles().stream().forEach(role -> {
					RoleRepresentation savedRoleRepresentation = realmResource.roles().get(role).toRepresentation();
					roleList.add(savedRoleRepresentation);
				});
				userResource.roles().realmLevel().add(roleList);

				// During the time of user creation some default roles are removed
				List<RoleRepresentation> roleRemoveList = new ArrayList<>();
				RoleRepresentation removeRoleOfflineAccess = realmResource.roles().get("offline_access")
						.toRepresentation();
				roleRemoveList.add(removeRoleOfflineAccess);
				RoleRepresentation removeRoleUmaAuthorization = realmResource.roles().get("uma_authorization")
						.toRepresentation();
				roleRemoveList.add(removeRoleUmaAuthorization);
				userResource.roles().realmLevel().remove(roleRemoveList);
				//Update info in databases for backup
				request.setUserId(userId);
				request.setPassword("XXXXXXX");
				UserDetailsVo userInfo = keyCloakUtil.getUserInfo("USER-ID");
				request.setCreateBy(userInfo.getUserId());
				userService.updateUserInfo(request);
				log.info("Roles assigned successfully");
			} else {			
				throw new BusinessException("" + result.getStatus());
			}
			log.info("Response : " + result.getStatus());
		} catch (Exception e) {
			log.error("Error reason: " + e.getMessage());
			if (e instanceof BusinessException) {
				if (e.getMessage().equals("409")) {
					throw new BusinessException("Invalid data", "ER_409");
				} else {
					throw new BusinessException(e.getMessage());
				}
			} else {
				throw new BusinessException(e.getMessage());
			}

		}

	}

	public ResponseVo logout() {
		log.info("inside createUser method KeyCloakService ");
		UsersResource usersResource = null;		
		try {
			//Keycloak kc = keyCloakUtil.getKeyCloakInstanceForUserCreation();
			RealmResource realmResource = keyCloakInstanceForUserCreation.realm(REALM);			
			UserDetailsVo userInfo = keyCloakUtil.getUserInfo("USER-ID");
			usersResource = realmResource.users();
			usersResource.get(userInfo.getUserId()).logout();
			keyCloakInstanceForUserCreation.realm(REALM).logoutAll();
		} catch (Exception e) {
			log.error("Error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
		return null;
	}
	
	public ResponseVo getTokenUsingRefreshToken(String refreshToken) {
		log.info("inside getTokenUsingRefreshToken method KeyCloakService ");
		ResponseVo response = null;
		String responseToken = null;
		try {
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
			urlParameters.add(new BasicNameValuePair("client_id", CLIENT_ID));
			urlParameters.add(new BasicNameValuePair("refresh_token", refreshToken));
			urlParameters.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
			
			responseToken = sendPost(urlParameters);

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(responseToken, ResponseVo.class);
			if (StringUtils.isNotBlank(response.getError())) {
				response.setStatus("failed");
			} else {
				response.setStatus("success");
			}
		}catch(Exception e) {
			log.error("Error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
		return response;
	}
	
	@org.springframework.cache.annotation.Cacheable("wardName")
	public ConfigVo getWardNameAndId() {
		log.info("inside getWardNameAndId method KeyCloakService ");
		ConfigVo response = new ConfigVo();;
		try {
			List<ConfigVo> resList = userDetailsMapper.getWardNameAndId();		
			Map<Integer,String> wardNameMap = new HashMap<>();
			resList.stream().forEach(res -> {				
				log.info(" Ward id : {} , Ward Name : {} ",res.getWardId(),res.getWardName());
				wardNameMap.put(res.getWardId(), res.getWardName());				
			});
			response.setWardNameMap(wardNameMap);
		}catch(Exception e) {
			log.error("Error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return response;
	}
	
	public void resetPassword(@RequestBody UserDetailsVo request) {
		log.info("inside resetPassword method KeyCloakService ");		
		UsersResource usersResource = null;	
		try {
			//Get username to validate the old password
			String username = userService.getUsernameByUserId(request);		
			
			
			UserCredentials userCredentials = new UserCredentials();
			userCredentials.setUsername(username);
			userCredentials.setPassword(request.getOldPassword());
			
			ResponseVo res = getToken(userCredentials);
			if(res.getStatus().equals("success")) {
				RealmResource realmResource = keyCloakInstanceForUserCreation.realm(REALM);
				usersResource = realmResource.users();
				UserDetailsVo userInfo = keyCloakUtil.getUserInfo("USER-ID");
				String userId = userInfo.getUserId();
				CredentialRepresentation passwordCred = new CredentialRepresentation();
				passwordCred.setTemporary(false);
				passwordCred.setType(CredentialRepresentation.PASSWORD);
				passwordCred.setValue(request.getNewPassword());
				UserResource userResource = usersResource.get(userId);
				userResource.resetPassword(passwordCred);
				log.info("Password reset successfully");
			}else {
				throw new BusinessException("Old password is not valid");
			}
		}catch(Exception e) {
			log.error("Error reason "+e.getMessage());
			if(e instanceof BusinessException) {
				throw new BusinessException(e.getMessage());
			}
			throw new BusinessException("Invalid password"+e.getMessage());
		}		
	}
	
}
