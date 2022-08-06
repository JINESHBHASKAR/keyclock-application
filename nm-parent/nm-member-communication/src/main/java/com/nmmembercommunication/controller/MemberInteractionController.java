package com.nmmembercommunication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nmmembercommunication.business.MemberInterationBusiness;
import com.nmmembercommunication.model.MeetingVo;
import com.nmmembercommunication.vo.ResponseVo;

@RestController
public class MemberInteractionController {
	private static final Logger log = LoggerFactory.getLogger(MemberInteractionController.class);
	
	@Autowired private MemberInterationBusiness memberInterationBusiness;
	
	@PostMapping(value = "/create-meeting")
	public ResponseVo createMeeting(@RequestBody MeetingVo request) {
		log.info("inside createMeeting method - MemberInteractionController");
		return memberInterationBusiness.createMeeting(request);
	}
	
	@GetMapping(value = "/get-meeting")
	public ResponseVo getAllMeeting() {
		log.info("inside getAllMeeting method - MemberInteractionController");
		return memberInterationBusiness.getAllMeeting();
	}
	
	@PostMapping(value = "/edit-meeting")
	public ResponseVo editScheduleMeeting(@RequestBody MeetingVo request) {
		log.info("inside editScheduleMeeting method - MemberInteractionController");
		return memberInterationBusiness.editScheduleMeeting(request);
	}

	@PostMapping(value = "/update-participants-attendance")
	public ResponseVo updateMemberAttendance(@RequestBody MeetingVo request) {
		log.info("inside markAttendance method - MemberInteractionController");
		return memberInterationBusiness.updateMemberAttendance(request);
	}
	
	@GetMapping(value = "/get-participants-list/{meetingId}")
	public ResponseVo getParticipantsList(@PathVariable(value = "meetingId") Integer meetingId) {
		log.info("inside getParticipantsList method - MemberInteractionController");		
		return memberInterationBusiness.getParticipantsList(meetingId);
	}
	
	@PostMapping(value = "/send-message")
	public ResponseVo sendMessage(@RequestBody MeetingVo request) {
		log.info("inside getParticipantsList method - MemberInteractionController");
		return memberInterationBusiness.sendMessage(request);
	}
	
	@PostMapping(value = "/upload-file")
	public ResponseVo uploadFile(@RequestParam("file") MultipartFile[] files) {
		log.info("inside uploadFile method - MemberInteractionController");		
		return memberInterationBusiness.uploadFile(files);
	}
	
	@GetMapping(value = "/get-image-name-list")
	public ResponseVo getImageNameList() {
		log.info("inside getImageNameList method - MemberInteractionController");		
		return memberInterationBusiness.getImageNameList();
	}
	
	@GetMapping(value = "/get-image/{filename}")
	public ResponseVo getImageByName(@PathVariable(value = "filename") String filename) {
		log.info("inside getImageNameList method - MemberInteractionController");		
		return memberInterationBusiness.getImageByName(filename);
	}
	
}
