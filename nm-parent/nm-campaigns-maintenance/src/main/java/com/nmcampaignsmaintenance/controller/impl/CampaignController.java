package com.nmcampaignsmaintenance.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nmcampaignsmaintenance.ValueObject.HomeScreenVo;
import com.nmcampaignsmaintenance.ValueObject.RequestVo;
import com.nmcampaignsmaintenance.ValueObject.ResponseVo;
import com.nmcampaignsmaintenance.ValueObject.ScreenResponseVo;
import com.nmcampaignsmaintenance.business.CampaignBusiness;
import com.nmcampaignsmaintenance.controller.CampaignControllerApi;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;

@RestController
@RequestMapping("/campaign")
public class CampaignController implements CampaignControllerApi{
	private static final Logger log = LoggerFactory.getLogger(CampaignController.class);
	
	@Autowired private CampaignBusiness campaignBusiness;
	
	@Override
	public HomeScreenVo getLoginLandingTemplate() {
		log.info("inside the method getLoginLandingTemplate - CampaignController");		
		return campaignBusiness.getLoginLandingTemplate();
	}
	
	@Override
	public ScreenResponseVo getCampaignDetails() {
		log.info("inside the method getCampaignDetails - CampaignController");	
		return campaignBusiness.getCampaignDetails();
	}

	@Override
	public ResponseVo collectFund(@RequestBody RequestVo request) {
		log.info("inside the method collectFund - CampaignController");
		return campaignBusiness.collectFund(request);				
	}

	@Override
	public ResponseVo getFundCollectionHistory(@PathVariable(name = "campaignId") Integer campaignId) {
		log.info("inside the method getFundCollectionHistory - CampaignController");		
		return campaignBusiness.getFundCollectionHistory(campaignId);
	}

	@Override
	public ResponseVo createCampaign(CampaignDetailVo request) {
		log.info("inside the method createCampaign - CampaignController");		
		return campaignBusiness.createCampaign(request);
	}
	
	@Override
	public ResponseVo enableDisableCampaign(@PathVariable(name = "campaignId") Integer campaignId,@PathVariable(name = "status") String status) {
		log.info("inside the method enableDisableCampaign - CampaignController");
		return campaignBusiness.enableDisableCampaign(campaignId,status);
	}
	
}
