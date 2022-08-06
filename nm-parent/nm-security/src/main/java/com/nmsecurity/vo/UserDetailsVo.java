package com.nmsecurity.vo;

import java.util.List;

public class UserDetailsVo extends StatusVo{

	private static final long serialVersionUID = 402168537149642873L;	
	
	private Integer id;	
	private Integer roleId;
	private String userId;
	private String name;
	private String tokenString;
	
	private String firstName;
	private String lastName;
	private String userName;
	private String mobile;
	private String email;
	private String createBy;
	private List<String> roles;
	private String password;
	private String oldPassword;
	private String newPassword;
	
	private String state;
	private String district;
	private String mandalam;
	private String postalCode;
	private String area;
	private String wardName;
	private String wardNo;
	
	private Integer wardId;	
	private Boolean isProfileStatusUpdated;
	private List<String> rolesAssignedByUser;
	private Boolean createUserEligibility;
	private Boolean enableSms;
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}		
		
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getMandalam() {
		return mandalam;
	}
	public void setMandalam(String mandalam) {
		this.mandalam = mandalam;
	}
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public List<String> getRolesAssignedByUser() {
		return rolesAssignedByUser;
	}
	public void setRolesAssignedByUser(List<String> rolesAssignedByUser) {
		this.rolesAssignedByUser = rolesAssignedByUser;
	}
	public Boolean getCreateUserEligibility() {
		return createUserEligibility;
	}
	public void setCreateUserEligibility(Boolean createUserEligibility) {
		this.createUserEligibility = createUserEligibility;
	}

	public Integer getWardId() {
		return wardId;
	}

	public void setWardId(Integer wardId) {
		this.wardId = wardId;
	}

	public Boolean getIsProfileStatusUpdated() {
		return isProfileStatusUpdated;
	}

	public void setIsProfileStatusUpdated(Boolean isProfileStatusUpdated) {
		this.isProfileStatusUpdated = isProfileStatusUpdated;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Boolean getEnableSms() {
		return enableSms;
	}

	public void setEnableSms(Boolean enableSms) {
		this.enableSms = enableSms;
	}	
}
