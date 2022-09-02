package com.nmsecurity.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jinesh KP
 *
 */

public class AuthResponse extends StatusVo implements Serializable{

	private static final long serialVersionUID = 6050165978288955998L;	

	private String username;
	private String authToken;
	private List<String> dashBoardKeyList;
	private Map<String,Object> dashBoardDetails;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public List<String> getDashBoardKeyList() {
		return dashBoardKeyList;
	}
	public void setDashBoardKeyList(List<String> dashBoardKeyList) {
		this.dashBoardKeyList = dashBoardKeyList;
	}
	public Map<String, Object> getDashBoardDetails() {
		return dashBoardDetails;
	}
	public void setDashBoardDetails(Map<String, Object> dashBoardDetails) {
		this.dashBoardDetails = dashBoardDetails;
	}
	
	
}
