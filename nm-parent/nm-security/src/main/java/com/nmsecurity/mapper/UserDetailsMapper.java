package com.nmsecurity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nmsecurity.model.UserCredentials;
import com.nmsecurity.vo.ConfigVo;
import com.nmsecurity.vo.UserDetailsVo;

@Mapper
public interface UserDetailsMapper {

	@Insert({
			"INSERT INTO user_details (userid,first_name,last_name,phone_no,email,createdby,username) values(#{userId},#{firstName},#{lastName},#{mobile},#{email},#{createBy},#{userName})" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public int updateUserInfo(UserDetailsVo request);

	@Insert({ "<script>", "INSERT INTO user_roles (userid,role_id) values ",
			"<foreach item='role' collection='roleList' separator= ',' open='' close='' >",
			"(#{role.userId},#{role.roleId})", "</foreach>", "</script>" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public int updateUserRoles(@Param("roleList") List<UserDetailsVo> roleList);

	@Results({ 				
				@Result(property = "userId", column = "userid"),
				@Result(property = "userName", column = "username"),				
				@Result(property = "firstName", column = "first_name"),				
				@Result(property = "lastName", column = "last_name"),
				@Result(property = "wardName", column = "ward_name"),
				@Result(property = "isProfileStatusUpdated", column = "profile_update_status"),
				@Result(property = "mandalam", column = "mandalam"),
				@Result(property = "district", column = "district"),
				@Result(property = "postalCode", column = "postal_code"),
				@Result(property = "area", column = "area")							
	})
	
	@Select("<script>"+
			"SELECT userid,username,first_name,last_name,ward_name,profile_update_status,mandalam,district,postal_code,area FROM user_details "+
			"<where> "+
			"<if test='userId != null'>userid = #{userId}</if>"+
			"<if test='wardId != null'>ward_name=(select KOTTAYAM_WARD_NAME from KOTTAYAM_WARD where ID = #{wardId})</if>"+
			"</where>"+
			"</script>")
		
	public List<UserDetailsVo> getUserProfileStatus(UserDetailsVo request);

	@Results({ @Result(property = "wardId", column = "ID"),
			@Result(property = "wardName", column = "KOTTAYAM_WARD_NAME") })
	@Select("select ID,KOTTAYAM_WARD_NAME from KOTTAYAM_WARD ;")
	public List<ConfigVo> getWardNameAndId();

	@Insert({
			"INSERT INTO user_device_token (username,device_token,status) values(#{username},#{deviceToken},#{status})" })
	public void storeDeviceToken(UserCredentials userCredentials);

	@Results({ @Result(property = "username", column = "username"),
			@Result(property = "deviceToken", column = "device_token") })
	@Select("select username,device_token from user_device_token where device_token = #{deviceToken}")
	public UserCredentials getUserDeviceDetails(@Param("deviceToken") String deviceToken);

	@Update({"update user_device_token set username = #{username} where device_token = #{deviceToken}"})
	public void updateUserDeviceInfo(UserCredentials userCredentials);
}
