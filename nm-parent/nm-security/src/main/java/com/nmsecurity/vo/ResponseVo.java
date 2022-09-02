package com.nmsecurity.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Jinesh KP
 *
 */

public class ResponseVo extends StatusVo{

	private static final long serialVersionUID = -2901974985787429958L;
	
	@JsonProperty("access_token")	
	private String accessToken;
	
	@JsonProperty("expires_in")	
	private String expiresIn;
	
	@JsonProperty("refresh_expires_in")	
	private String refreshExpiresIn;
	
	@JsonProperty("refresh_token")	
	private String refreshToken;
	
	@JsonProperty("token_type")		
	private String tokenType;
	
	@JsonProperty("not-before-policy")
	private String notBeforePolicy;
	
		
	@JsonProperty("session_state")	
	private String sessionState;
	
	
	@JsonProperty("scope")
	private String scope;
	
	private Boolean isUserNameAvailable;
	private Boolean isMobileNumberAvailable;
	private List<UserDetailsVo> memberList;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshExpiresIn() {
		return refreshExpiresIn;
	}
	public void setRefreshExpiresIn(String refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getNotBeforePolicy() {
		return notBeforePolicy;
	}
	public void setNotBeforePolicy(String notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}
	public String getSessionState() {
		return sessionState;
	}
	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Boolean getIsUserNameAvailable() {
		return isUserNameAvailable;
	}
	public void setIsUserNameAvailable(Boolean isUserNameAvailable) {
		this.isUserNameAvailable = isUserNameAvailable;
	}
	public Boolean getIsMobileNumberAvailable() {
		return isMobileNumberAvailable;
	}
	public void setIsMobileNumberAvailable(Boolean isMobileNumberAvailable) {
		this.isMobileNumberAvailable = isMobileNumberAvailable;
	}
	public List<UserDetailsVo> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<UserDetailsVo> memberList) {
		this.memberList = memberList;
	}
	
}
