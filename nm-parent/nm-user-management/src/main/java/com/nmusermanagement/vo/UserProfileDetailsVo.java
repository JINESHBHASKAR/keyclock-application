package com.nmusermanagement.vo;

import java.io.Serializable;
import java.util.List;

public class UserProfileDetailsVo implements Serializable{

	private static final long serialVersionUID = 4069998129916207288L;

	private String userId;
	private String firstName;
	private String lastName;
    private String phone;
    private String email;
	private String address;
	private String wardName;
	private String wardNo;	
	private String district;
	private String postalCode;
	private String state;
	private Boolean profileStatus;	
	private List<String> roles;
	private WardDetailsVo wardDetail;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public String getWardNo() {
		return wardNo;
	}
	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Boolean getProfileStatus() {
		return profileStatus;
	}
	public void setProfileStatus(Boolean profileStatus) {
		this.profileStatus = profileStatus;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public final WardDetailsVo getWardDetail() {
		return wardDetail;
	}
	public final void setWardDetail(WardDetailsVo wardDetail) {
		this.wardDetail = wardDetail;
	}
	
}
