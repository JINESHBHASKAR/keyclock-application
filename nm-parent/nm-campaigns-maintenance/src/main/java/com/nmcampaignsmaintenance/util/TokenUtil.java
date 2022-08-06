package com.nmcampaignsmaintenance.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TokenUtil {
	private final static Logger log = LoggerFactory.getLogger(TokenUtil.class);
	
	@Autowired private HttpServletRequest httpServletRequest;
	
	public Object getUserInfo(String key) {
		log.info("inside getUserInfo method : TokenUtil - key {}",key);
		httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		KeycloakSecurityContext kp = ((KeycloakPrincipal<?>) httpServletRequest.getUserPrincipal()).getKeycloakSecurityContext();		
		AccessToken token = kp.getToken();		
		if(key.equals("USER-ROLES")) {			
			Set<String> str = token.getRealmAccess().getRoles();
			List<String> roleList = new ArrayList<>(str);							
			return roleList;
		}else if(key.equals("USER-ID")) {
			return token.getSubject();
		}
		return null;
	}
}
