package com.nmcampaignsmaintenance.ValueObject;

import java.util.List;

import com.nmcampaignsmaintenance.model.CampaignDetailVo;

public class ScreenResponseVo extends StatusVo{
	
	private static final long serialVersionUID = -1101978510728129657L;
	
	private List<CampaignDetailVo> campaignDetailList;

	public List<CampaignDetailVo> getCampaignDetailList() {
		return campaignDetailList;
	}

	public void setCampaignDetailList(List<CampaignDetailVo> campaignDetailList) {
		this.campaignDetailList = campaignDetailList;
	}
	
}
