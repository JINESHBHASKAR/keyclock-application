package com.nmcampaignsmaintenance.ValueObject;

import java.io.Serializable;

public class MemberDetailsVo implements Serializable{

	private static final long serialVersionUID = 827099640292484806L;
	
	private String firstName;
	private String lastName;
	private String mobile;
	private String wardName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	
	

}
