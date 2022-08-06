package com.nmcampaignsmaintenance.ValueObject;

import java.io.Serializable;
import java.util.Date;

public class RequestVo implements Serializable{
	
	private static final long serialVersionUID = -4943681070362296165L;
	
	//collect-fund
	private Integer campaignId;
	private String fundDonorName;
	private Float amount;
	private String mobile;
	private Date donatedOn;
	private String receiptPrefix;
	private Float totalFundCollected;
	private Integer transactionType;
	private String transactionId;
	
	private String receiptId;
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public String getFundDonorName() {
		return fundDonorName;
	}
	public void setFundDonorName(String fundDonorName) {
		this.fundDonorName = fundDonorName;
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
	public Date getDonatedOn() {
		return donatedOn;
	}
	public void setDonatedOn(Date donatedOn) {
		this.donatedOn = donatedOn;
	}
	public String getReceiptPrefix() {
		return receiptPrefix;
	}
	public void setReceiptPrefix(String receiptPrefix) {
		this.receiptPrefix = receiptPrefix;
	}
	public Float getTotalFundCollected() {
		return totalFundCollected;
	}
	public void setTotalFundCollected(Float totalFundCollected) {
		this.totalFundCollected = totalFundCollected;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public Integer getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}	
}
