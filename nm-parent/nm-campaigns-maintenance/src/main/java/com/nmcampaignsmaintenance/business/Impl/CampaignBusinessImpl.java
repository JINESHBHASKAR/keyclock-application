package com.nmcampaignsmaintenance.business.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nmcampaignsmaintenance.ValueObject.HomeScreenVo;
import com.nmcampaignsmaintenance.ValueObject.RequestVo;
import com.nmcampaignsmaintenance.ValueObject.ResponseVo;
import com.nmcampaignsmaintenance.ValueObject.ScreenResponseVo;
import com.nmcampaignsmaintenance.ValueObject.TileDetailsVo;
import com.nmcampaignsmaintenance.business.CampaignBusiness;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;
import com.nmcampaignsmaintenance.model.GeneralPublicInfoVo;
import com.nmcampaignsmaintenance.model.ReceiptDetailsVo;
import com.nmcampaignsmaintenance.service.CampaignServices;
import com.nmcampaignsmaintenance.util.CommonUtil;
import com.nmcampaignsmaintenance.util.DateUtil;
import com.nmcampaignsmaintenance.util.TokenUtil;

@Component
public class CampaignBusinessImpl implements CampaignBusiness{
	private static final Logger log = LoggerFactory.getLogger(CampaignBusinessImpl.class);	
	
	@Autowired private CampaignServices campaignServices;
	@Autowired private TokenUtil tokenUtil;
	@Autowired private CommonUtil commonUtil;	
	
	public HomeScreenVo getLoginLandingTemplate() {
		log.info("inside getLoginLandingTemplate method - CampaignBusinessImpl");		
		@SuppressWarnings("unchecked")
		List<String> roleList = (List<String>) tokenUtil.getUserInfo("USER-ROLES");				
		HomeScreenVo response = new HomeScreenVo();
		Map<String, TileDetailsVo> tileDetailMap = new HashMap<>();
		List<String> tileList = new ArrayList<>();
		tileList.add("fundcollection");
		tileList.add("messaging");
		tileList.add("meetings");
		tileList.add("reports");
		tileList.add("addUser");
		//Add user tile not for member only role
		if(roleList.size()==1 && roleList.get(0).equals("MEMBER")) {
			tileList.remove("addUser");
			tileList.remove("messaging");
		}
		response.setTileList(tileList);
		response.setStatus("success");
		
		TileDetailsVo fundCollectionTileDetails = new TileDetailsVo();
		fundCollectionTileDetails.setTileName("FUND COLLECTIONS");
		tileDetailMap.put("fundcollection", fundCollectionTileDetails);
		
		TileDetailsVo messagingTileDetails = new TileDetailsVo();
		messagingTileDetails.setTileName("MESSAGING");
		tileDetailMap.put("messaging", messagingTileDetails);
		
		TileDetailsVo meetingsTileDetails = new TileDetailsVo();
		meetingsTileDetails.setTileName("MEETINGS");
		tileDetailMap.put("meetings", meetingsTileDetails);
		
		TileDetailsVo reportsTileDetails = new TileDetailsVo();
		reportsTileDetails.setTileName("REPORTS");
		tileDetailMap.put("reports", reportsTileDetails);
		
		TileDetailsVo addUserTileDetails = new TileDetailsVo();
		addUserTileDetails.setTileName("ADD USER");
		tileDetailMap.put("addUser", addUserTileDetails);
		
		response.setTileDetails(tileDetailMap);
		
		return response;
	}

	@Override
	public ScreenResponseVo getCampaignDetails() {
		log.info("inside getCampaignDetails method - CampaignBusinessImpl");
		ScreenResponseVo response = new ScreenResponseVo();
		response.setStatus("success");		
		@SuppressWarnings("unchecked")
		List<CampaignDetailVo> campaignDetailList = campaignServices.getCampaignDetails();
		List<String> roleList = (List<String>) tokenUtil.getUserInfo("USER-ROLES");	
		campaignDetailList.stream().forEach(camp -> {
			camp.setFormattedDate(DateUtil.convertDateToGivenStringPattern(camp.getCreatedDate(),DateUtil.DATE_PATTERN_dd_MM_YYYY));
			if(!(roleList.contains("SUPER-ADMIN") || roleList.contains("DISTRICT"))) {
				camp.setShowCampaingStatusUpdateButton(false);
			}else {
				camp.setShowCampaingStatusUpdateButton(true);
			}
		});
		
		if(!(roleList.contains("SUPER-ADMIN") || roleList.contains("DISTRICT"))) {
			campaignDetailList.removeIf(new Predicate<CampaignDetailVo>() {
				@Override
				public boolean test(CampaignDetailVo campaign) {					
					return campaign.getStatus().equals("INACTIVE");
				}
			});
		}
		List<CampaignDetailVo> campaignDetailListSortedByCreated = campaignDetailList.stream()
																					 .sorted((campaign1,campaign2)-> campaign2.getCreatedDate().compareTo(campaign1.getCreatedDate()))
																					 .collect(Collectors.toList());
		response.setCampaignDetailList(campaignDetailListSortedByCreated);
		return response;
	}

	/*public static void main(String[] args) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 
		try {
			// Date date = formatter.parse("31-03-2015");
			 List<Date> dl = Arrays.asList(formatter.parse("30/08/2020 11:34:23"),
					 formatter.parse("12/09/2020 11:22:23"),
					 formatter.parse("13/09/2020 11:34:23"),
					 formatter.parse("12/09/2020 11:34:23"));
			 List<Date> dlsorted = dl.stream().sorted((dl1,dl2) -> dl2.compareTo(dl1)).collect(Collectors.toList());
			 System.out.println(dlsorted);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseVo collectFund(RequestVo request) {
		log.info("inside collectFund method - CampaignBusinessImpl");
		ResponseVo response = new ResponseVo();
		String userId = (String) tokenUtil.getUserInfo("USER-ID");		
		GeneralPublicInfoVo publicUserInfo = new GeneralPublicInfoVo(request.getFundDonorName(),request.getAmount(),request.getMobile(),request.getCampaignId(),request.getDonatedOn(),userId,request.getTransactionId(),request.getTransactionType());		
		//Concat the request.getReceiptPrefix() with the id generated from general public;
		campaignServices.commonInsert(publicUserInfo);
		ReceiptDetailsVo receiptInfo = new ReceiptDetailsVo(request.getReceiptPrefix()+publicUserInfo.getId(),request.getCampaignId());
		campaignServices.commonInsert(receiptInfo);
		//Update the campaign_details - total fund column
		CampaignDetailVo campaignDetail = new CampaignDetailVo();
		campaignDetail.setId(request.getCampaignId());
		campaignDetail.setTotalFundCollected(request.getTotalFundCollected()+request.getAmount());
		campaignServices.commonUpdate(campaignDetail);
		response.setStatus("success");
		response.setPersonName(publicUserInfo.getName());
		response.setReceiptId(receiptInfo.getReceipt());
		response.setMobile(request.getMobile());
		response.setAmount(publicUserInfo.getAmount());
		//info for message
		request.setReceiptId(receiptInfo.getReceipt());
		commonUtil.sendReceiptInfoViaSMS(request);
		response.setTotalFundCollected(campaignDetail.getTotalFundCollected());
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseVo getFundCollectionHistory(Integer campaignId) {
		log.info("inside getFundCollectionHistory method - CampaignBusinessImpl");
		GeneralPublicInfoVo request = new GeneralPublicInfoVo();
		//Get USER Role
		List<String> userRoles = (List<String>) tokenUtil.getUserInfo("USER-ROLES");
		if(userRoles.size()==1 && userRoles.get(0).equals("MEMBER")) {
			String userId = (String) tokenUtil.getUserInfo("USER-ID");
			request.setFundCollectedBy(userId);
		}
		//Get userID				
		request.setCampaignId(campaignId);
		List<GeneralPublicInfoVo> res = campaignServices.getAllGeneralPublicData(request);
		//Format the date and time 
		res.stream()
					.forEach(user -> {
						user.setFormattedDate(DateUtil.convertDateToGivenStringPattern(user.getDonatedOn(),DateUtil.DATE_PATTERN_dd_MM_YYYY));
						user.setTimeStamp(DateUtil.convertDateToGivenStringPattern(user.getDonatedOn(),DateUtil.TIME_PATTERN_HH_mm));
					});
		List<GeneralPublicInfoVo> resSorted =  res.stream().sorted((user1,user2) -> user2.getDonatedOn().compareTo(user1.getDonatedOn())).collect(Collectors.toList());
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		response.setFundDonarList(resSorted);
		return response;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResponseVo createCampaign(CampaignDetailVo request) {
		log.info("inside createCampaign method - CampaignBusinessImpl");
		
		//Need to implement the security to get the user details
		//then only campaign created user can be added so temporarily set to be 2
		String userId = (String) tokenUtil.getUserInfo("USER-ID");
		request.setCreatedDate(DateUtil.getCurrentDate());
		CampaignDetailVo req = new CampaignDetailVo(
								request.getCampaignName(),
								request.getDescription(),
								request.getCreatedDate(),
								"ACTIVE", //Status always ACTIVE during creation, it can be inactivated only at the end, access to the specified user
								userId,
								request.getMinimumFund(),
								request.getShortName(),
								0f);	//Default total fund value set to be	
		campaignServices.commonInsert(req);		
		//Format date
		req.setFormattedDate(DateUtil.convertDateToGivenStringPattern(req.getCreatedDate(),DateUtil.DATE_PATTERN_dd_MM_YYYY));
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		response.setCampDetail(req);
		return response;
	}

	@Override
	public ResponseVo enableDisableCampaign(Integer campaignId,String status) {		
		log.info("inside createCampaign method - CampaignBusinessImpl");
		CampaignDetailVo req = new CampaignDetailVo();
		req.setKey("CAMPAIGN_STATUS_UPDATE");
		req.setStatus(status);
		req.setId(campaignId);
		campaignServices.commonUpdate(req);
		ResponseVo response = new ResponseVo();
		response.setStatus("success");
		return response;
	}

}