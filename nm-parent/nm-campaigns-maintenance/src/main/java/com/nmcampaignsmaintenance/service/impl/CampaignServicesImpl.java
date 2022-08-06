package com.nmcampaignsmaintenance.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nmcampaignsmaintenance.mapper.CampaignMapper;
import com.nmcampaignsmaintenance.model.CampaignDetailVo;
import com.nmcampaignsmaintenance.model.GeneralPublicInfoVo;
import com.nmcampaignsmaintenance.model.ReceiptDetailsVo;
import com.nmcampaignsmaintenance.service.CampaignServices;
import com.nmcommon.exception.BusinessException;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class CampaignServicesImpl implements CampaignServices<Object,Object>{
	private static final Logger log = LoggerFactory.getLogger(CampaignServicesImpl.class);
	
	@Autowired private CampaignMapper campaignMapper;
	
	@Override
	public List<CampaignDetailVo> getCampaignDetails() {
		log.info("inside getLoginLandingTemplate method - CampaignBusinessImpl");
		List<CampaignDetailVo> campaignDetailList = null;
		try {
			campaignDetailList = campaignMapper.getCampaignDetails();
		}catch(Exception e) {
			log.error("error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		return campaignDetailList;
	}

	@Override
	public Object commonInsert(Object object) {
		log.info("inside getLoginLandingTemplate method - CampaignBusinessImpl");
		try {
			if(object instanceof GeneralPublicInfoVo) {
				log.info("insert record in general_public_info table");
				GeneralPublicInfoVo request = (GeneralPublicInfoVo) object;
				campaignMapper.insertGeneralPublic(request);			
				return request;
			}else if(object instanceof ReceiptDetailsVo) {
				log.info("insert record in receipt_details table");
				ReceiptDetailsVo request = (ReceiptDetailsVo) object;
				campaignMapper.insertReceiptDetails(request);
				return request;
			}else if(object instanceof CampaignDetailVo ){
				log.info("insert record in campaign_details table");
				CampaignDetailVo request = (CampaignDetailVo) object;
				campaignMapper.createCampaign(request);
				return request;
			}
		}catch(Exception e) {
			log.error("error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
		
		return null;
	}

	@Override
	public Object commonUpdate(Object req) {
		log.info("inside commonUpdate method - CampaignBusinessImpl");
		try {
			if(req instanceof CampaignDetailVo) {
				CampaignDetailVo request = (CampaignDetailVo) req;
				if(StringUtils.isNotBlank(request.getKey()) && request.getKey().equals("CAMPAIGN_STATUS_UPDATE")) {
					log.info("Update campaign_details update status");
					campaignMapper.updateCampaignDetailFund(request);
				}else {
					log.info("Update campaign_details table");					
					campaignMapper.updateCampaignDetailFund(request);
					return req;
				}				
			}
		}catch(Exception e) {
			log.error("error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}		
		return null;
	}

	@Override
	public List<GeneralPublicInfoVo> getAllGeneralPublicData(GeneralPublicInfoVo request) {
		log.info("inside commonUpdate method - CampaignBusinessImpl");
		List<GeneralPublicInfoVo> fundDonarListByCampid = null;
		try {
			fundDonarListByCampid = campaignMapper.getAllGeneralPublicData(request);
		}catch(Exception e) {
			log.error("error reason "+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		
		return fundDonarListByCampid;
	}
	
}

