package com.nmsecurity.model;

import java.io.Serializable;

/**
 * 
 * @author Jinesh KP
 *
 */

public class AuthRequest implements Serializable{

	private static final long serialVersionUID = 8381547954061644693L;
	private String mobile;
	private String email;
	private String otp;
	private String userid;
	
	//Login
	private String userName;
	private String password;
	private String role;
	private boolean roleEnable;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isRoleEnable() {
		return roleEnable;
	}
	public void setRoleEnable(boolean roleEnable) {
		this.roleEnable = roleEnable;
	}
	@Override
	public String toString() {
		return "AuthRequest [mobile=" + mobile + ", email=" + email + ", otp=" + otp + ", userid=" + userid
				+ ", userName=" + userName + ", password=" + password + ", role=" + role + ", roleEnable=" + roleEnable
				+ "]";
	}
	
	
	
	 		 	 
}
