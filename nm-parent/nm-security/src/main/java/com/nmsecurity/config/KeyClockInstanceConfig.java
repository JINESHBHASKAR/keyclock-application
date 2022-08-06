package com.nmsecurity.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyClockInstanceConfig {
	private final static Logger log = LoggerFactory.getLogger(KeyClockInstanceConfig.class);
	@Value("${keycloak.resource}")	
	private String CLIENT_ID;
	
	@Value("${keycloak.credentials.secret}")
	private String CLIENT_SECRET;
	
	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;
	
	@Value("${keycloak.realm}")
	private String REALM;
	
	@Value("${master-realm-keyCloak.realm}")
	private String mRealm;
	
	@Value("${master-realm-keyCloak.name}")	
	private String mClientName;
	
	@Value("${master-realm-keyCloak.secret}")
	private String mClientSecret;
	
	@Value("${master-realm-keyCloak.user}")	
	private String USER;
	
	@Value("${master-realm-keyCloak.pass}")
	private String PASSWORD;
	
	@Bean(name = "keyCloakInstanceForTokenCreation")
	public Keycloak getKeyCloakInstanceForTokenCreation() {
		log.info("inside getKeyCloakInstanceForTokenCreation method : KeyClockInstanceConfig");
		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm(REALM).username(USER).password(PASSWORD)
				.clientId(CLIENT_ID).clientSecret(CLIENT_SECRET).resteasyClient(new ResteasyClientBuilder().connectionPoolSize(2).build())
				.build();
		
		return kc;
	}
	
	@Bean(name = "keyCloakInstanceForUserCreation")
	public Keycloak getKeyCloakInstanceForUserCreation() {
		log.info("inside getKeyCloakInstanceForUserCreation method : KeyClockInstanceConfig");
		Keycloak kc = KeycloakBuilder.builder().serverUrl(AUTHURL).realm(mRealm).username(USER).password(PASSWORD)
				.clientId(mClientName).clientSecret(mClientSecret).resteasyClient(new ResteasyClientBuilder().connectionPoolSize(2).build())
				.build();
		
		return kc;
	}
	
}
