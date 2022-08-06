package com.nmcampaignsmaintenance.model;

import java.io.Serializable;
import java.util.Date;

import com.nmcampaignsmaintenance.ValueObject.MemberDetailsVo;

public class GeneralPublicInfoVo implements Serializable{
	
	private static final long serialVersionUID = 6504886590831110929L;
	private Integer id;
	private String name;
	private Float amount;
	private String mobile;
	private Integer campaignId;
	private Date donatedOn;
	private String fundCollectedBy;
	private String transactionId;	
	private Integer transactionTypeId;
	private String formattedDate;
	private String timeStamp;
	
	private MemberDetailsVo fundCollectorDetails;
	public GeneralPublicInfoVo() {		
	}		
	
	public GeneralPublicInfoVo(String name, Float amount, String mobile, Integer campaignId, Date donatedOn,
			String fundCollectedBy, String transactionId,Integer transactionTypeId) {
		super();
		this.name = name;
		this.amount = amount;
		this.mobile = mobile;
		this.campaignId = campaignId;
		this.donatedOn = donatedOn;
		this.fundCollectedBy = fundCollectedBy;
		this.transactionId = transactionId;
		this.transactionTypeId = transactionTypeId;
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
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public Date getDonatedOn() {
		return donatedOn;
	}
	public void setDonatedOn(Date donatedOn) {
		this.donatedOn = donatedOn;
	}
	public String getFundCollectedBy() {
		return fundCollectedBy;
	}
	public void setFundCollectedBy(String fundCollectedBy) {
		this.fundCollectedBy = fundCollectedBy;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}	
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public MemberDetailsVo getFundCollectorDetails() {
		return fundCollectorDetails;
	}
	public void setFundCollectorDetails(MemberDetailsVo fundCollectorDetails) {
		this.fundCollectorDetails = fundCollectorDetails;
	}	
}

