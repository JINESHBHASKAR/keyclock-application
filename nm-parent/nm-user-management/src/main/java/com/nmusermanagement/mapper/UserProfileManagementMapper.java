package com.nmusermanagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.nmusermanagement.vo.CommonRequestVo;
import com.nmusermanagement.vo.UserProfileDetailsVo;
import com.nmusermanagement.vo.WardDetailsVo;

@Mapper
public interface UserProfileManagementMapper {
	
	@Results({
		@Result(property = "wardId", column = "ID"),
		@Result(property = "wardName", column = "KOTTAYAM_WARD_NAME"),
		@Result(property = "governingBodyId", column = "GOVERNING_BODY_ID"),
		@Result(property = "mpName", column = "NAME"),
		@Result(property = "gramaPanchayatId", column = "KOTTAYAM_GRAMA_PANCHAYAT_BLOCK_ID"),
		@Result(property = "gramaPanchayatName", column = "KOTTAYAM_GRAMA_PANCHAYAT_BLOCK_NAME"),
		@Result(property = "districtInfoId", column = "KOTTAYAM_DISTRICT_INFO_ID"),	
		@Result(property = "goveningBodyType", column = "GOVERNING_BODY_TYPE")
	})
	@Select("SELECT\r\n" + 
			"	KW.ID,KW.KOTTAYAM_WARD_NAME, KW.GOVERNING_BODY_ID,\r\n" + 
			"	KMPD.NAME,KMPD.KOTTAYAM_GRAMA_PANCHAYAT_BLOCK_ID,KGPB.KOTTAYAM_GRAMA_PANCHAYAT_BLOCK_NAME,KMPD.KOTTAYAM_DISTRICT_INFO_ID,\r\n" + 
			"	KDI.GOVERNING_BODY_TYPE    \r\n" + 
			"	FROM KOTTAYAM_WARD AS KW\r\n" + 
			"	INNER JOIN KOTTAYAM_MUNICIPALITY_AND_PANCHAYAT_DETAILS AS KMPD ON KW.GOVERNING_BODY_ID = KMPD.ID\r\n" + 
			"	INNER JOIN KOTTAYAM_DISTRICT_INFO AS KDI ON KMPD.KOTTAYAM_DISTRICT_INFO_ID = KDI.ID\r\n" + 
			"	INNER JOIN KOTTAYAM_GRAMA_PANCHAYAT_BLOCK AS KGPB ON KMPD.KOTTAYAM_GRAMA_PANCHAYAT_BLOCK_ID = KGPB.ID where KW.KOTTAYAM_WARD_NAME = #{wardName}")	
	public WardDetailsVo getWardDetails(String wardName);
	
	@Insert({"update user_details set state=#{state},"
			+ " district=#{district},"
			+ " area=#{address},"
			+ " ward_name=#{wardName},"
			+ " postal_code=#{postalCode},"
			+ " profile_update_status=#{profileStatus}"
			+ " where userid=#{userId}"})
	public int userProfileUpdate(UserProfileDetailsVo request);
	
	
	@Results({
		@Result(property = "userId", column = "userid"),
		@Result(property = "firstName", column = "first_name"),
		@Result(property = "lastName", column = "last_name"),
		@Result(property = "phone", column = "phone_no"),
		@Result(property = "email", column = "email"),
		@Result(property = "profileStatus", column = "profile_update_status"),
		@Result(property = "wardName", column = "ward_name"),
		@Result(property = "roles", column = "userid", javaType = List.class, many = @Many(select = "getUserRoleList") )
	})
	@Select("<script>"
			+ "select userid,first_name,last_name,phone_no,email,profile_update_status,ward_name from user_details "
			+ "<where>"
			+ "<if test='wardNameList !=null'>"
			+ "ward_name in "
			+ "<foreach item='wardName' collection='wardNameList'  separator= ',' open='(' close=')' >"
			+ "#{wardName}"
			+ "</foreach> AND "
			+ "</if>"
			+ "ward_name IS NOT NULL"
			+ "</where> "			
			+ "</script>")
	public List<UserProfileDetailsVo> getMemberList(@Param("wardNameList") List<String> wardNameList);
	
	@Select("select r.role from user_roles ur join roles r on ur.role_id = r.role_id where ur.userid = #{userId}")
	public List<String> getUserRoleList(@Param("userId") String userId);
	
	@Results({
		@Result(property = "wardName", column = "ward_name"),		
	})	
	
	@Select("select ward_name from user_details where userid = #{userId}")
	public UserProfileDetailsVo getUserDetails(CommonRequestVo req);
	
	@Results({
		@Result(property = "wardName", column = "KOTTAYAM_WARD_NAME"),		
	})
	@Select("select  KOTTAYAM_WARD_NAME from KOTTAYAM_WARD where GOVERNING_BODY_ID = (select GOVERNING_BODY_ID from KOTTAYAM_WARD where KOTTAYAM_WARD_NAME=#{wardName})")
	public List<WardDetailsVo> getWardList(CommonRequestVo req);
	
}
