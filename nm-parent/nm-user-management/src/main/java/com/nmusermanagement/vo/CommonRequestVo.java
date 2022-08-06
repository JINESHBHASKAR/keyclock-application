package com.nmusermanagement.vo;

import java.io.Serializable;

public class CommonRequestVo implements Serializable{
	
	private static final long serialVersionUID = 3952476657092892846L;
	private String userId;
	private String wardName;	
	private String memberRole;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public String getMemberRole() {
		return memberRole;
	}
	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}
	
}
