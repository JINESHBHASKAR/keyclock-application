package com.nmcampaignsmaintenance.model;

import java.io.Serializable;

public class ReceiptDetailsVo implements Serializable{

	private static final long serialVersionUID = 4288683120047945620L;
	
	private Integer id;
	private String receipt;
    private Integer campaignId;
	    
    public ReceiptDetailsVo(String receipt, Integer campaignId) {
		this.receipt = receipt;
		this.campaignId = campaignId;
	}
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}        
	
}
