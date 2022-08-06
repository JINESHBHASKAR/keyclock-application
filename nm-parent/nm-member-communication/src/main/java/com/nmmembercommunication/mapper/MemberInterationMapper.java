package com.nmmembercommunication.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nmmembercommunication.model.MeetingAttendanceDetailsVo;
import com.nmmembercommunication.model.MeetingVo;
import com.nmmembercommunication.vo.UserDetailsVo;

@Mapper
public interface MemberInterationMapper {
	
	@Insert({"INSERT INTO meeting (meeting_name,created_by,createdOn,meeting_location,ward_id,schedule_date_time) "
			+ "values(#{name},#{creatorId},#{creationTime},#{location},#{wardId},#{scheduleDateTime})"})
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
	public int createMeeting(MeetingVo request);

	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "meeting_name"),
		@Result(property = "scheduleDateTime", column = "schedule_date_time",javaType = java.util.Date.class),
		@Result(property = "creatorId", column = "created_by"),
		@Result(property = "creationTime", column = "createdOn"),
		@Result(property = "location", column = "meeting_location"),
		@Result(property = "wardId", column = "ward_id"),
		@Result(property = "status", column = "status"),
		@Result(property = "meetingUpdateInfoList", column = "id", javaType = List.class, many = @Many(select = "getMeetingLastUpdatedInfo"))
	})
	@Select("<script>"
			+ "SELECT * FROM meeting"
			+ "<where>"
			+ "<if test = 'id != null'>id = #{id}</if>"
			+ "</where>"			
			+ "</script>")
	public List<MeetingVo> getAllMeeting(Integer id);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "meetingId", column = "meeting_id"),
		@Result(property = "lastUpdateBy", column = "updated_by"),
		@Result(property = "lastUpdateTime", column = "updated_time")
	})
	@Select("select * from meeting_update_info where meeting_id = #{id}")
	public List<MeetingVo> getMeetingLastUpdatedInfo(@Param("id") Integer id);
	
	@Update({"<script>"
			+ "update meeting "
			+ "<set>"
			+ "<if test = 'name != null'> meeting_name = #{name},</if>"
			+ " <if test = 'scheduleDateTime != null'> schedule_date_time = #{scheduleDateTime},</if>"
			+ " <if test = 'location != null'> meeting_location = #{location},</if>"
			+ " <if test = 'wardId != null'> ward_id = #{wardId},</if>"
			+ " <if test = 'status != null'> status = #{status}</if>"
			+ " <if test = 'meetingAttendance !=null'> meeting_attendance = #{meetingAttendance} </if>"
			+ "</set>"
			+ " where id=#{id}"
			+ "</script>"})
	public int editScheduleMeeting(MeetingVo request);
	
	@Insert({"INSERT INTO meeting_update_info (meeting_id,updated_by,updated_time) "
			+ "values(#{id},#{lastUpdateBy},#{lastUpdateTime})"})
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
	public int meetingUpdateInfo(MeetingVo request);


	@Select("<script>"
			+ "select device_token from user_device_token where username in "
			+ "(select username from user_details where userid in "
			+ "<foreach item='participantUserId' collection='participantsUserIdList' separator= ',' open='(' close=')' >"
			+ "#{participantUserId}"
			+ "</foreach>)"
			+ "</script>")
	public List<String> getTokenList(@Param("participantsUserIdList") List<String> participantsUserIdList);
	
	@Insert({"<script>", 
		"INSERT INTO meeting_attendance_details (meeting_id,userid) VALUES ",
		"<foreach item='participant' collection='participantsList' separator= ',' open='' close='' >",
			"(#{participant.meetingId},#{participant.userId})",
		"</foreach>",
		"</script>"})
	public void meetingAttendanceDetails(@Param("participantsList") List<MeetingAttendanceDetailsVo> participantsList);
	
	@Update({"update meeting_attendance_details set attendance = #{attendance} where userid = #{userId} and meeting_id = #{meetingId}"})
	public void updateMemberAttendance(MeetingAttendanceDetailsVo participant);
	
	@Results({
		@Result(property = "userId", column = "userid"),
		@Result(property = "firstName", column = "first_name"),
		@Result(property = "lastName", column = "last_name"),
		@Result(property = "mobile", column = "phone_no"),
		@Result(property = "wardName", column = "ward_name"),
		@Result(property = "meetingId", column = "meeting_id"),
		@Result(property = "attendance", column = "attendance")
	})
	@Select("<script>"
			+ "select ud.userid,ud.first_name,ud.last_name,ud.phone_no,ud.ward_name,ma.meeting_id,ma.attendance from user_details ud join meeting_attendance_details ma  on ud.userid =ma.userid "
			+ "<if test = 'meetingId != null'>"
			+ "and ma.meeting_id = #{meetingId}"
			+ "</if>"
			+ "</script>")
	public List<UserDetailsVo> getParticipantsList(@Param("meetingId")  Integer meetingId);
	

}
