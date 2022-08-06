package com.nmmembercommunication.vo;

import java.util.List;

import com.nmmembercommunication.model.MeetingVo;

public class ResponseVo extends StatusVo{

	private static final long serialVersionUID = 5521353096803257408L;	
	
	private List<MeetingVo> meetingList;
	private List<MeetingVo> activeMeetingList;
	private List<MeetingVo> inActiveMeetingList;
	private List<UserDetailsVo> meetingParticipantsList;
	private List<String> imageNameList;
	private byte[] img;
	private Boolean meetingStatus;
	
	private String displayMessage;
	
	public List<MeetingVo> getMeetingList() {
		return meetingList;
	}
	public void setMeetingList(List<MeetingVo> meetingList) {
		this.meetingList = meetingList;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	public List<UserDetailsVo> getMeetingParticipantsList() {
		return meetingParticipantsList;
	}
	public void setMeetingParticipantsList(List<UserDetailsVo> meetingParticipantsList) {
		this.meetingParticipantsList = meetingParticipantsList;
	}
	public List<MeetingVo> getActiveMeetingList() {
		return activeMeetingList;
	}
	public void setActiveMeetingList(List<MeetingVo> activeMeetingList) {
		this.activeMeetingList = activeMeetingList;
	}
	public List<MeetingVo> getInActiveMeetingList() {
		return inActiveMeetingList;
	}
	public void setInActiveMeetingList(List<MeetingVo> inActiveMeetingList) {
		this.inActiveMeetingList = inActiveMeetingList;
	}
	public List<String> getImageNameList() {
		return imageNameList;
	}
	public void setImageNameList(List<String> imageNameList) {
		this.imageNameList = imageNameList;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public Boolean getMeetingStatus() {
		return meetingStatus;
	}
	public void setMeetingStatus(Boolean meetingStatus) {
		this.meetingStatus = meetingStatus;
	}	
	
}
