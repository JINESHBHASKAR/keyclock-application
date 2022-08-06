package com.nmmembercommunication.vo;

import java.io.Serializable;

import com.nmmembercommunication.model.MeetingAttendanceDetailsVo;

public class UserDetailsVo implements Serializable {

	private static final long serialVersionUID = 8359407386764816502L;
	private String userId;
	private String firstName;
	private String lastName;
	private String mobile;
	private String wardName;
	private Integer meetingId;	
	private Boolean attendance;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Boolean getAttendance() {
		return attendance;
	}
	public void setAttendance(Boolean attendance) {
		this.attendance = attendance;
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
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}		

}
