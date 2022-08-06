package com.nmcampaignsmaintenance.business;

import com.nmcampaignsmaintenance.ValueObject.HomeScreenVo;
import com.nmcampaignsmaintenance.ValueObject.RequestVo;
import com.nmcampaignsmaintenance.ValueObject.ResponseVo;
import com.nmcampaignsmaintenance.ValueObject.ScreenResponseVo;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;

public interface CampaignBusiness {
	public HomeScreenVo getLoginLandingTemplate();
	public ScreenResponseVo getCampaignDetails();
	public ResponseVo collectFund(RequestVo request);
	public ResponseVo getFundCollectionHistory(Integer campaignId);
	public ResponseVo createCampaign(CampaignDetailVo request);
	public ResponseVo enableDisableCampaign(Integer campaignId,String status);
}
