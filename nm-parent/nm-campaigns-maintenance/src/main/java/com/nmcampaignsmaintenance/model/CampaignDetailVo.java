package com.nmcampaignsmaintenance.model;

import java.io.Serializable;
import java.util.Date;

public class CampaignDetailVo implements Serializable{	
	private static final long serialVersionUID = 2767740118628123535L;
	private Integer id;
	private String campaignName;
	private String description;
	private Date createdDate;
	private String status;
	private String createdBy;	
	private String minimumFund;
	private String receiptPrefix;	
	private Float totalFundCollected;	
	
	private String shortName;
	private String formattedDate;
	private String key;
	
	private Boolean showCampaingStatusUpdateButton;
	public CampaignDetailVo() {		
	}
	
	
	public CampaignDetailVo(String campaignName, String description, Date createdDate, String status,
			String createdBy, String minimumFund, String receiptPrefix, Float totalFundCollected) {		
		this.campaignName = campaignName;
		this.description = description;
		this.createdDate = createdDate;
		this.status = status;
		this.createdBy = createdBy;
		this.minimumFund = minimumFund;
		this.receiptPrefix = receiptPrefix;
		this.totalFundCollected = totalFundCollected;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMinimumFund() {
		return minimumFund;
	}
	public void setMinimumFund(String minimumFund) {
		this.minimumFund = minimumFund;
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Boolean getShowCampaingStatusUpdateButton() {
		return showCampaingStatusUpdateButton;
	}
	public void setShowCampaingStatusUpdateButton(Boolean showCampaingStatusUpdateButton) {
		this.showCampaingStatusUpdateButton = showCampaingStatusUpdateButton;
	}
	
}

