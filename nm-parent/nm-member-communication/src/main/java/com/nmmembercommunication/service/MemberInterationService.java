package com.nmmembercommunication.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nmcommon.exception.BusinessException;
import com.nmmembercommunication.mapper.MemberInterationMapper;
import com.nmmembercommunication.model.MeetingAttendanceDetailsVo;
import com.nmmembercommunication.model.MeetingVo;
import com.nmmembercommunication.util.DateUtil;
import com.nmmembercommunication.vo.PushNotificationRequest;
import com.nmmembercommunication.vo.ResponseVo;
import com.nmmembercommunication.vo.UserDetailsVo;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MemberInterationService {
	private static final Logger log = LoggerFactory.getLogger(MemberInterationService.class);

	@Autowired
	private MemberInterationMapper memberInterationMapper;
	@Autowired
	private PushNotificationService pushNotificationService;

	public ResponseVo createMeeting(MeetingVo request) {
		log.info("inside createMeeting method - MemberInterationService");
		try {
			memberInterationMapper.createMeeting(request);
			log.info("Meeting created  success : Meeting id ==================> {} ", request.getId());
			// Update the participant list
			List<MeetingAttendanceDetailsVo> participantsList = new ArrayList<>();
			request.getParticipantsUserIdList().forEach(userId -> {
				MeetingAttendanceDetailsVo mem = new MeetingAttendanceDetailsVo();
				mem.setUserId(userId);
				mem.setMeetingId(request.getId());
				participantsList.add(mem);
			});
						
			memberInterationMapper.meetingAttendanceDetails(participantsList);
			log.info("Participant list Created");
			
			// Get Tokens list			
			List<String> tokenList = memberInterationMapper.getTokenList(request.getParticipantsUserIdList());
			String title = "Meeting Alert";
			String message = request.getName()+" on "+request.getLocation()+" at : "+request.getScheduleDateTime();
			PushNotificationRequest req = new PushNotificationRequest(title,message,tokenList);
			pushNotificationService.sendNotificationAndDataMessage(req);			 
		} catch (Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return null;
	}

	public List<MeetingVo> getAllMeeting() {
		log.info("inside createMeeting method - MemberInterationService");
		List<MeetingVo> meetingList = null;
		try {
			meetingList = memberInterationMapper.getAllMeeting(null);
		} catch (Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return meetingList;
	}

	public void editScheduleMeeting(MeetingVo request) {
		log.info("inside editScheduleMeeting method - MemberInterationService");
		try {
			memberInterationMapper.editScheduleMeeting(request);
			log.info("Meeting updated  success");
			memberInterationMapper.meetingUpdateInfo(request);
			log.info("Meeting updated  info stored successfully");
			// Get Tokens list			
			if(request.getParticipantsUserIdList() != null && !request.getParticipantsUserIdList().isEmpty()) {
				List<String> tokenList = memberInterationMapper.getTokenList(request.getParticipantsUserIdList());
				if(tokenList != null && !tokenList.isEmpty()) {
					String title = "Meeting Update Alert";
					String meetingTime = DateUtil.convertDateToStringPattern(request.getScheduleDateTime(),DateUtil.DATE_PATTERN_dd_MMM_YYYY_HH_MM);
					String message = request.getName()+" on "+meetingTime+" at : "+request.getLocation();
					//Only allow push notification if meeting status is true
					if(request.getStatus()) {
						PushNotificationRequest req = new PushNotificationRequest(title,message,tokenList);
						pushNotificationService.sendNotificationAndDataMessage(req);
					}else {
						message = request.getName()+" - Meeting Closed";
						PushNotificationRequest req = new PushNotificationRequest(title,message,tokenList);
						pushNotificationService.sendNotificationAndDataMessage(req);
					}
				}else {
					throw new BusinessException("Unable to send notification to the participants");
				}
			}else {
				throw new BusinessException("No participants available");
			}
									
		} catch (Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}

	}
	
	public void updateMemberAttendance(MeetingVo request) {
		log.info("inside updateMemberAttendance method - MemberInterationService");
		try {
			request.getMemberAttendanceList().stream().forEach(req -> {
				memberInterationMapper.updateMemberAttendance(req);
			});
			log.info("member attendance updated ");
			MeetingVo req = new MeetingVo();
			req.setMeetingAttendance(true);
			req.setId(request.getId());
			memberInterationMapper.editScheduleMeeting(req);
			log.info("Attendance closed ");
		}catch(Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	public List<UserDetailsVo> getParticipantsList(Integer meetingId) {
		log.info("inside getParticipantsList method - MemberInterationService");
		List<UserDetailsVo> response = null;
		try {
			response = memberInterationMapper.getParticipantsList(meetingId);
		}catch(Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return response;
	}
	
	public List<UserDetailsVo> getAllParticipantsList(){
		log.info("inside getAllParticipantsList method - MemberInterationService");
		List<UserDetailsVo> response = null;
		try {
			response = memberInterationMapper.getParticipantsList(null);
		}catch(Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return response;
	}
	
	public void sendMessage(MeetingVo request) {
		log.info("inside sendMessage method - MemberInterationService");
		try {
			List<String> tokenList = memberInterationMapper.getTokenList(request.getParticipantsUserIdList());			
			PushNotificationRequest req = new PushNotificationRequest(request.getMsgTitle(),request.getMessage(),tokenList);
			pushNotificationService.sendNotificationAndDataMessage(req);
		}catch(Exception e) {
			log.error("Error reason " + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
}
