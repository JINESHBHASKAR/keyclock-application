package com.nmcampaignsmaintenance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nmcampaignsmaintenance.ValueObject.HomeScreenVo;
import com.nmcampaignsmaintenance.ValueObject.RequestVo;
import com.nmcampaignsmaintenance.ValueObject.ResponseVo;
import com.nmcampaignsmaintenance.ValueObject.ScreenResponseVo;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;

@RestController
public interface CampaignControllerApi {	
	
	@GetMapping(value = "/get-login-landing-info")
	public HomeScreenVo getLoginLandingTemplate();
	
	@GetMapping(value = "/get-campaign-details")
	public ScreenResponseVo getCampaignDetails();
	
	@PostMapping(value = "/collect-fund")
	public ResponseVo collectFund(@RequestBody RequestVo request);
	
	@GetMapping(value = "/get-fund-collection-history/{campaignId}")
	public ResponseVo getFundCollectionHistory(@PathVariable(name = "campaignId") Integer campaignId);
	
	@PostMapping(value = "/create-campaign")
	public ResponseVo createCampaign(@RequestBody CampaignDetailVo request);
	
	@GetMapping(value = "/update-campaign-status/{campaignId}/{status}")
	public ResponseVo enableDisableCampaign(@PathVariable(name = "campaignId") Integer campaignId,@PathVariable(name = "status") String status);
}
