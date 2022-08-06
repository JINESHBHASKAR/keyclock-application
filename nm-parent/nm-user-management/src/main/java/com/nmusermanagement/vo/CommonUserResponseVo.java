package com.nmusermanagement.vo;

import java.util.List;

public class CommonUserResponseVo extends StatusVo {

	private static final long serialVersionUID = 8502396746643590061L;
	private WardDetailsVo wardDetails;
	private List<UserProfileDetailsVo> memberList;
	
	public WardDetailsVo getWardDetails() {
		return wardDetails;
	}
	public void setWardDetails(WardDetailsVo wardDetails) {
		this.wardDetails = wardDetails;
	}
	public List<UserProfileDetailsVo> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<UserProfileDetailsVo> memberList) {
		this.memberList = memberList;
	}
		
}
