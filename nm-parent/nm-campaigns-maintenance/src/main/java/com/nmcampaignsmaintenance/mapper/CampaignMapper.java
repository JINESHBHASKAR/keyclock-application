package com.nmcampaignsmaintenance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nmcampaignsmaintenance.ValueObject.MemberDetailsVo;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;
import com.nmcampaignsmaintenance.model.GeneralPublicInfoVo;
import com.nmcampaignsmaintenance.model.ReceiptDetailsVo;

@Mapper
public interface CampaignMapper {
	
	
	@Insert({"insert into campaign_details (Name_of_the_campaign,description,starting_date,status,created_by,min_fund_limit,recipet_prfx,total_fund) "
			+ "values (#{campaignName},#{description},#{createdDate},#{status},#{createdBy},#{minimumFund},#{receiptPrefix},#{totalFundCollected})"})
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
	public int createCampaign(CampaignDetailVo request);
	
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "campaignName", column = "Name_of_the_campaign"),
		@Result(property = "description", column = "description"),
		@Result(property = "createdDate", column = "starting_date"),
		@Result(property = "status", column = "status"),
		@Result(property = "mobile", column = "mobile"),
		@Result(property = "createdBy", column = "created_by"),
		@Result(property = "minimumFund", column = "min_fund_limit"),
		@Result(property = "receiptPrefix", column = "recipet_prfx"),		
		@Result(property = "totalFundCollected", column = "total_fund")
	})
	@Select("SELECT * FROM campaign_details")
	public List<CampaignDetailVo> getCampaignDetails();
	
	
	@Results({
		@Result(property = "donatedOn", column = "donated_on"),
		@Result(property = "fundCollectorDetails", column = "fund_collected_by", javaType = MemberDetailsVo.class, one = @One(select = "getMemberDetailsByID"))
	})	
	
	@Select("<script>"+
			"SELECT * FROM general_public_info "
			+ "WHERE donated_to_campaign_id = #{campaignId} "+			
			"<if test='fundCollectedBy != null'>AND fund_collected_by = #{fundCollectedBy}</if>"+			
			"</script>")	
	public List<GeneralPublicInfoVo> getAllGeneralPublicData(GeneralPublicInfoVo request);
	
	@Results({
		@Result(property = "firstName", column = "first_name"),
		@Result(property = "lastName", column = "last_name"),
		@Result(property = "mobile", column = "phone_no"),
		@Result(property = "wardName", column = "ward_name"),
	})
	@Select("select first_name,last_name,phone_no,ward_name from user_details where userid = #{userId} ")
	public MemberDetailsVo getMemberDetailsByID(@Param("userId") String userId);
	
	@Insert({"INSERT INTO general_public_info (name,amount,mobile,donated_to_campaign_id,donated_on,fund_collected_by,transaction_id,transaction_type_id) values(#{name},#{amount},#{mobile},#{campaignId},#{donatedOn},#{fundCollectedBy},#{transactionId},#{transactionTypeId})"})
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
	public int insertGeneralPublic(GeneralPublicInfoVo request);
	
	@Insert({"INSERT INTO receipt_details (receipt,receipt_for_campaign) values(#{receipt},#{campaignId})"})
	@Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
	public int insertReceiptDetails(ReceiptDetailsVo request);
	
	@Update({"<script>"
			+ "update campaign_details"
			+ "<set>"
			+ "<if test = 'totalFundCollected!=null'>"
			+ " total_fund=#{totalFundCollected}"
			+ "</if>"
			+ "<if test = 'status!=null'>"
			+ " status=#{status}"
			+ "</if>"
			+ "</set>"
			+ " where id=#{id}"
			+ "</script>"})
	public void updateCampaignDetailFund(CampaignDetailVo request);
	
	
	
}
