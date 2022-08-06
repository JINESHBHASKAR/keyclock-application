package com.nmmembercommunication.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nmmembercommunication.vo.UserDetailsVo;

public class MeetingVo implements Serializable{
	
	private static final long serialVersionUID = 7520181276019995367L;
	private Integer id;
	private String name;
	private String creatorId;
	private String lastUpdateBy;
	private Date lastUpdateTime;
	private Date creationTime;
	private String location;
	private Integer wardId;
	private Boolean status;
	private Date scheduleDateTime;
	private String scheduleDate;
	private String scheduleTime;
	private Boolean meetingAttendance;
	private List<MeetingAttendanceDetailsVo> memberAttendanceList;
	private List<UserDetailsVo> participantList;
	private List<String> participantsUserIdList;
	private Boolean isEditable;	
	
	private Integer totalParticipants;
	private Long numberOfAvailableParticipants;
	private Long numberOfUnAvailableParticipants;
	
	private List<MeetingVo> meetingUpdateInfoList; 
	private Integer meetingId;
	private Date recentlyUpdateTime;
	private String msgTitle;
	private String message;
	
	public MeetingVo() {
	}
	public MeetingVo(String name, String creatorId, Date creationTime, String location, Integer wardId,Date scheduleDateTime,List<String> participantsUserIdList) {
		super();
		this.name = name;
		this.creatorId = creatorId;
		this.creationTime = creationTime;
		this.location = location;
		this.wardId = wardId;
		this.scheduleDateTime = scheduleDateTime;
		this.participantsUserIdList = participantsUserIdList;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getScheduleDateTime() {
		return scheduleDateTime;
	}
	public void setScheduleDateTime(Date scheduleDateTime) {
		this.scheduleDateTime = scheduleDateTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getWardId() {
		return wardId;
	}
	public void setWardId(Integer wardId) {
		this.wardId = wardId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	public Integer getTotalParticipants() {
		return totalParticipants;
	}
	public void setTotalParticipants(Integer totalParticipants) {
		this.totalParticipants = totalParticipants;
	}
	public Long getNumberOfAvailableParticipants() {
		return numberOfAvailableParticipants;
	}
	public void setNumberOfAvailableParticipants(Long numberOfAvailableParticipants) {
		this.numberOfAvailableParticipants = numberOfAvailableParticipants;
	}
	public Long getNumberOfUnAvailableParticipants() {
		return numberOfUnAvailableParticipants;
	}
	public void setNumberOfUnAvailableParticipants(Long numberOfUnAvailableParticipants) {
		this.numberOfUnAvailableParticipants = numberOfUnAvailableParticipants;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public List<UserDetailsVo> getParticipantList() {
		return participantList;
	}
	public void setParticipantList(List<UserDetailsVo> participantList) {
		this.participantList = participantList;
	}
	public List<String> getParticipantsUserIdList() {
		return participantsUserIdList;
	}
	public void setParticipantsUserIdList(List<String> participantsUserIdList) {
		this.participantsUserIdList = participantsUserIdList;
	}
	public Boolean getMeetingAttendance() {
		return meetingAttendance;
	}
	public void setMeetingAttendance(Boolean meetingAttendance) {
		this.meetingAttendance = meetingAttendance;
	}
	public List<MeetingAttendanceDetailsVo> getMemberAttendanceList() {
		return memberAttendanceList;
	}
	public void setMemberAttendanceList(List<MeetingAttendanceDetailsVo> memberAttendanceList) {
		this.memberAttendanceList = memberAttendanceList;
	}
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	public List<MeetingVo> getMeetingUpdateInfoList() {
		return meetingUpdateInfoList;
	}
	public void setMeetingUpdateInfoList(List<MeetingVo> meetingUpdateInfoList) {
		this.meetingUpdateInfoList = meetingUpdateInfoList;
	}
	public Date getRecentlyUpdateTime() {
		return recentlyUpdateTime;
	}
	public void setRecentlyUpdateTime(Date recentlyUpdateTime) {
		this.recentlyUpdateTime = recentlyUpdateTime;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
