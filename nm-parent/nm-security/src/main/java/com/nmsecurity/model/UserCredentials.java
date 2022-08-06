package com.nmsecurity.model;

import java.io.Serializable;

public class UserCredentials implements Serializable {

	private static final long serialVersionUID = -6224784402770718231L;	
	private String password;
	private String username;
	private String deviceToken;
	private Boolean status;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


}
