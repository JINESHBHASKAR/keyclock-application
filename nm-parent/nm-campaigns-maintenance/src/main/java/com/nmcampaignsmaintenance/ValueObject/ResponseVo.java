package com.nmcampaignsmaintenance.ValueObject;

import java.util.List;
import java.util.Map;

import com.nmcampaignsmaintenance.model.CampaignDetailVo;
import com.nmcampaignsmaintenance.model.GeneralPublicInfoVo;

public class ResponseVo extends StatusVo{
	
	private static final long serialVersionUID = -5857153033084286669L;
	private String receiptId;
	private String personName;
	private String mobile;
	private Float amount;
	private Float totalFundCollected;
	private List<GeneralPublicInfoVo> fundDonarList;
	private CampaignDetailVo campDetail;
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Float getTotalFundCollected() {
		return totalFundCollected;
	}
	public void setTotalFundCollected(Float totalFundCollected) {
		this.totalFundCollected = totalFundCollected;
	}
	public List<GeneralPublicInfoVo> getFundDonarList() {
		return fundDonarList;
	}
	public void setFundDonarList(List<GeneralPublicInfoVo> fundDonarList) {
		this.fundDonarList = fundDonarList;
	}
	public CampaignDetailVo getCampDetail() {
		return campDetail;
	}
	public void setCampDetail(CampaignDetailVo campDetail) {
		this.campDetail = campDetail;
	}
		
}
