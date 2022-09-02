package com.nmsecurity.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nmsecurity.config.AppConfig;
import com.nmsecurity.vo.UserDetailsVo;

/**
 * 
 * @author Jinesh KP
 *
 */

@Component
public class KeyCloakUtil {
	private final static Logger log = LoggerFactory.getLogger(KeyCloakUtil.class);
	
	@Autowired private HttpServletRequest httpServletRequest;
	
	@Autowired private AppConfig appConfig;
	
	public UserDetailsVo getUserInfo(String key) {
		log.info("inside getUserInfo method : KeyCloakUtil - key {}",key);
		httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) httpServletRequest.getUserPrincipal();
		KeycloakSecurityContext kp = ((KeycloakPrincipal<?>) keycloakAuthenticationToken.getPrincipal()).getKeycloakSecurityContext();
		AccessToken token = kp.getToken();
		UserDetailsVo response = new UserDetailsVo();
		if(key.equals("USER-DETAILS")) {									
			response.setUserId(token.getSubject());
			response.setName(token.getName());
			
			Collection<GrantedAuthority> authorities = keycloakAuthenticationToken.getAuthorities();
			List<String> roleList = new ArrayList<>();
			Set<String> rolesAssignedByUser = new HashSet<>();
			authorities.stream().forEach(authority -> {
				roleList.add(authority.getAuthority());				
			});
			response.setRoles(roleList);
			roleList.stream().forEach(role->{
				if(appConfig.getRoleGrantLevel().get(role)!=null) {
					List<String> roleAssignList = appConfig.getRoleGrantLevel().get(role);
					if(!roleAssignList.isEmpty()) {
						roleAssignList.stream().forEach(roleToBeAssign->{
							rolesAssignedByUser.add(roleToBeAssign);
						});
					}
				}								
			});			
			response.setRolesAssignedByUser(new ArrayList<>(rolesAssignedByUser));
			response.setCreateUserEligibility((rolesAssignedByUser.isEmpty())?false:true);		
			Map<String, Object> otherClaims = token.getOtherClaims();
			if(!otherClaims.isEmpty()) {
				String mobile = (String) otherClaims.get("mobile");
				if(StringUtils.isNotBlank(mobile))
					response.setMobile(mobile);
			}			
		}else if(key.equals("AUTH-TOKEN")) {				
			response.setTokenString(kp.getTokenString());			
		}else if(key.equals("USER-ID")) {
			response.setUserId(token.getSubject());
		}else if(key.equals("USER-ROLES")) {
			Collection<GrantedAuthority> authorities = keycloakAuthenticationToken.getAuthorities();
			List<String> roleList = new ArrayList<>();			
			authorities.stream().forEach(authority -> {
				roleList.add(authority.getAuthority());				
			});
			response.setRoles(roleList);
		}
		return response;
	}
	
}
