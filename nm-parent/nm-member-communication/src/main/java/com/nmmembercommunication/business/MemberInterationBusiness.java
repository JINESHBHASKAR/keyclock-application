package com.nmmembercommunication.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nmcommon.exception.BusinessException;
import com.nmmembercommunication.model.MeetingVo;
import com.nmmembercommunication.service.MemberInterationService;
import com.nmmembercommunication.util.DateUtil;
import com.nmmembercommunication.util.TokenUtil;
import com.nmmembercommunication.vo.ResponseVo;
import com.nmmembercommunication.vo.UserDetailsVo;

@Component
public class MemberInterationBusiness {
	private static final Logger log = LoggerFactory.getLogger(MemberInterationBusiness.class);
	
	@Autowired private MemberInterationService memberInterationService; 
	@Autowired private TokenUtil tokenUtil;
	
	@Value("${spring.servlet.multipart.location}")	
	private String imgDirPath;
	
	public ResponseVo createMeeting(MeetingVo request) {		
		log.info("inside createMeeting method - MemberInterationBusiness");
		Date meetingDateTime = null;
		if(StringUtils.isNotBlank(request.getScheduleDate()) && StringUtils.isNotBlank(request.getScheduleTime())) {
			String dateTime = request.getScheduleDate()+" "+request.getScheduleTime();
			meetingDateTime = DateUtil.convertStringToDateTime(dateTime);
			if(request.getParticipantsUserIdList()==null || request.getParticipantsUserIdList().isEmpty()) {
				throw new BusinessException("No member selected");
			}			
		}else {
			throw new BusinessException("Invalid date and time");
		}				
		Date time = DateUtil.getCurrentDate();
		String userId = (String) tokenUtil.getUserInfo("USER-ID");
		MeetingVo req = new MeetingVo(request.getName(),userId,time,request.getLocation(),request.getWardId(),meetingDateTime,request.getParticipantsUserIdList());		
		memberInterationService.createMeeting(req);		
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		return response;
	}
	
	public ResponseVo getAllMeeting() {
		log.info("inside getAllMeeting method - MemberInterationBusiness");
		ResponseVo response = new ResponseVo();
		List<MeetingVo> activeMeetingList = new ArrayList<>();
		List<MeetingVo> inActiveMeetingList = new ArrayList<>();
		List<MeetingVo> meetingList = memberInterationService.getAllMeeting();
		List<UserDetailsVo> participantsDetails = memberInterationService.getAllParticipantsList();		
		response.setStatus("success");		
		if(meetingList!=null && !meetingList.isEmpty()) {			
			@SuppressWarnings("unchecked")
			List<String> roleList = (List<String>) tokenUtil.getUserInfo("USER-ROLES");
			meetingList.stream().forEach(meeting -> {
				List<UserDetailsVo> participantList  = new ArrayList<>(); 
				meeting.setIsEditable(false);				
				if(meeting.getStatus() && !(roleList.size() == 1 && roleList.contains("MEMBER"))) {
					meeting.setIsEditable(true);
				}
				
				participantsDetails.stream().forEach(participant -> {					
					if(meeting.getId().equals(participant.getMeetingId())){
						log.info("meeting.getId() : {} == participant.getMeetingId() : {}",meeting.getId(),participant.getMeetingId());
						participantList.add(participant);
					}
					
				});
				
				if(!meeting.getStatus()) {												
					meeting.setNumberOfAvailableParticipants(participantList.stream().filter(participant -> participant.getAttendance().equals(true)).collect(Collectors.counting())); 					
					meeting.setNumberOfUnAvailableParticipants(participantList.stream().filter(participant -> participant.getAttendance().equals(false)).collect(Collectors.counting()));					
				}				
				meeting.setTotalParticipants(participantList.size());
				meeting.setParticipantList(participantList);
			});
			
			meetingList.stream().forEach(meeting -> {
				Date meetingScheduleTime = DateUtil.addHoursToJavaUtilDate(meeting.getScheduleDateTime());
				meeting.setScheduleDateTime(meetingScheduleTime);
				Date meetingCreationTime = DateUtil.addHoursToJavaUtilDate(meeting.getCreationTime());
				meeting.setCreationTime(meetingCreationTime);
				//Meeting update list
				if(meeting.getMeetingUpdateInfoList() != null && !meeting.getMeetingUpdateInfoList().isEmpty()) {
					meeting.getMeetingUpdateInfoList().stream().forEach(meetingUpdateInfo -> {
						Date meetingUpdateTime = DateUtil.addHoursToJavaUtilDate(meetingUpdateInfo.getLastUpdateTime());
						meetingUpdateInfo.setLastUpdateTime(meetingUpdateTime);				
					});
					//Store the Recently updated info  
					List<MeetingVo> meetingUpdateInfoSorted  = meeting.getMeetingUpdateInfoList().stream().sorted((meeting1,meeting2) -> meeting2.getLastUpdateTime().compareTo(meeting1.getLastUpdateTime())).collect(Collectors.toList());					
					meeting.setRecentlyUpdateTime(meetingUpdateInfoSorted.get(0).getLastUpdateTime());					
				}
				
				
			});
			
			meetingList.stream().forEach(meeting -> {
				if(meeting.getStatus()) {
					activeMeetingList.add(meeting);
				}else {
					inActiveMeetingList.add(meeting);
				}
			});
			
			List<MeetingVo> activeMeetingSorted  = activeMeetingList.stream().sorted((meeting1,meeting2) -> meeting1.getScheduleDateTime().compareTo(meeting2.getScheduleDateTime())).collect(Collectors.toList());			
			response.setActiveMeetingList(activeMeetingSorted);
			if(!(roleList.size() == 1 && roleList.contains("MEMBER"))) {
				List<MeetingVo> InActiveMeetingSortedBasedOnRecentUpdate = inActiveMeetingList.stream().sorted((meeting1,meeting2) -> meeting2.getRecentlyUpdateTime().compareTo(meeting1.getRecentlyUpdateTime())).collect(Collectors.toList());
				response.setInActiveMeetingList(InActiveMeetingSortedBasedOnRecentUpdate);
			}			
			response.setMeetingList(meetingList);			
		}else {
			response.setMeetingList(new ArrayList<>());
			response.setActiveMeetingList(activeMeetingList);
			response.setInActiveMeetingList(inActiveMeetingList);
			response.setDisplayMessage("No Meetings Schedule");
		}
		return response;
	}
	
	public ResponseVo editScheduleMeeting(MeetingVo request) {
		log.info("inside editScheduleMeeting method - MemberInterationBusiness");
		Date meetingDateTime = null;		
		if(StringUtils.isNotBlank(request.getScheduleDate()) && StringUtils.isNotBlank(request.getScheduleTime())) {
			String dateTime = request.getScheduleDate()+" "+request.getScheduleTime();
			meetingDateTime = DateUtil.convertStringToDateTime(dateTime);
			request.setScheduleDateTime(meetingDateTime);
		}else {
			throw new BusinessException("Invalid Time and Date");
		}
		Date updatedtime = DateUtil.getCurrentDate();
		String userId = (String) tokenUtil.getUserInfo("USER-ID");
		request.setLastUpdateBy(userId);
		request.setLastUpdateTime(updatedtime);
		boolean isAllowableToCloseMeeting = false;
		if(request.getStatus().equals(false)) {
			if(request.getCreatorId().equals(userId)) {
				isAllowableToCloseMeeting = true;
			}
		}else {
			isAllowableToCloseMeeting = true;
		}
		if(isAllowableToCloseMeeting) {
			memberInterationService.editScheduleMeeting(request);
		}else {
			throw new BusinessException("Your not authorized person to close this meeting");
		}		
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		response.setMeetingStatus(request.getStatus());
		return response;
	}
	
	public ResponseVo updateMemberAttendance(MeetingVo request) {
		log.info("inside updateMemberAttendance method - MemberInterationBusiness");		
		String userId = (String) tokenUtil.getUserInfo("USER-ID");
		if(!request.getCreatorId().equals(userId)) {
			throw new BusinessException("Your not authorized person make attendance");
		}else {
			request.getMemberAttendanceList().stream().forEach(member -> {
				member.setMeetingId(request.getId());					
			});
			memberInterationService.updateMemberAttendance(request);
			ResponseVo response = new ResponseVo();
			response.setStatus("success");
			return response;
		}
	}
	
	public ResponseVo getParticipantsList(Integer meetingId) {
		log.info("inside getParticipantsList method - MemberInterationBusiness");
		List<UserDetailsVo> response = memberInterationService.getParticipantsList(meetingId);
		ResponseVo res = new ResponseVo();
		res.setStatus("success");
		if(response != null && !response.isEmpty()) {			
			res.setMeetingParticipantsList(response);
		}else {
			res.setMeetingParticipantsList(new ArrayList<>());
		}
		return res;
	}
	
	public ResponseVo sendMessage(MeetingVo request) {
		log.info("inside sendMessage method - MemberInterationBusiness");
		memberInterationService.sendMessage(request);
		ResponseVo res = new ResponseVo();
		res.setStatus("success");
		return res;
	}
	
	public ResponseVo uploadFile(MultipartFile[] files){
		log.info("inside uploadFile method - MemberInterationBusiness");
		try {
			Arrays.asList(files).stream().forEach(file ->{
				try {
					file.transferTo(new File(file.getOriginalFilename()));
				} catch (Exception e) {					
					throw new BusinessException(e.getMessage());
				}
			}); 
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		return response;
	}
	
	public ResponseVo getImageNameList() {
		log.info("inside getImageNameList method - MemberInterationBusiness");
		File file = new File(imgDirPath);
		List<String> imageNameList = null;
		try {
			String[] fileList = file.list();
			imageNameList = new ArrayList<>();
			for(String name:fileList){
	            System.out.println(name);
	            imageNameList.add(name);
	        }
		}catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
        ResponseVo response = new ResponseVo();
        response.setStatus("success");
        response.setImageNameList(imageNameList);
        return response;
	}
	
	public ResponseVo getImageByName(String filename) {
		log.info("inside getImageByName method - MemberInterationBusiness");
		byte[] bytesArray = null;
		FileInputStream fileInputStream = null;
		try {
			File file = new File(imgDirPath+filename);
			bytesArray = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		}catch(Exception e) {
			throw new BusinessException("The system cannot find the file specified");
		}finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                	throw new BusinessException(e.getMessage());
                }
            }
		}
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		response.setImg(bytesArray);
		return response;
	}	
}
