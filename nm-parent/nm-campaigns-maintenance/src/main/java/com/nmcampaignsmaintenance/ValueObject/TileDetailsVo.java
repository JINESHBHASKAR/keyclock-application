package com.nmcampaignsmaintenance.ValueObject;

import java.io.Serializable;

public class TileDetailsVo implements Serializable{
	
	private static final long serialVersionUID = 1564723910559611775L;
	
	private String tileName;

	public String getTileName() {
		return tileName;
	}

	public void setTileName(String tileName) {
		this.tileName = tileName;
	}
	

}
