package com.nmcampaignsmaintenance.ValueObject;

import java.io.Serializable;

public class StatusVo implements Serializable{
	
	private static final long serialVersionUID = 6642747525757860731L;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
