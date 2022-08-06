package com.nmcampaignsmaintenance.ValueObject;

import java.util.List;
import java.util.Map;

public class HomeScreenVo extends StatusVo{
	
	private static final long serialVersionUID = 6114501913978360677L;

	private List<String> tileList;
	private Map<String, TileDetailsVo> tileDetails;
	
	
	public List<String> getTileList() {
		return tileList;
	}

	public void setTileList(List<String> tileList) {
		this.tileList = tileList;
	}

	public Map<String, TileDetailsVo> getTileDetails() {
		return tileDetails;
	}

	public void setTileDetails(Map<String, TileDetailsVo> tileDetails) {
		this.tileDetails = tileDetails;
	}		
		
}
