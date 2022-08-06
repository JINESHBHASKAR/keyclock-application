package com.nmmembercommunication.model;

import java.io.Serializable;

public class MeetingAttendanceDetailsVo implements Serializable{
	
	private static final long serialVersionUID = -6088329629305098565L;
	private Integer id;
	private Integer meetingId;
	private String userId;
	private Boolean attendance;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
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
		
}
