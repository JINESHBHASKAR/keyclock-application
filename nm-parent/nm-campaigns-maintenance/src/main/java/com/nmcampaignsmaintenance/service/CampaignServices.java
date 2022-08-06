package com.nmcampaignsmaintenance.service;

import java.util.List;

import com.nmcampaignsmaintenance.model.CampaignDetailVo;
import com.nmcampaignsmaintenance.model.GeneralPublicInfoVo;

public interface CampaignServices<R,A> {
	public List<CampaignDetailVo> getCampaignDetails();
	public R commonInsert(A a);
	public R commonUpdate(A a);
	public List<GeneralPublicInfoVo> getAllGeneralPublicData(GeneralPublicInfoVo request);
}
