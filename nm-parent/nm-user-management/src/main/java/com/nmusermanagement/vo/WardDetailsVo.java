package com.nmusermanagement.vo;

import java.io.Serializable;

public class WardDetailsVo implements Serializable{

	private static final long serialVersionUID = -5923427917652894680L;
	
	private Integer wardId;
	private String wardName;
	private Integer governingBodyId;
	private String mpName;
	private Integer gramaPanchayatId;
	private String gramaPanchayatName;
	private Integer districtInfoId;
	private String goveningBodyType;
	
	public Integer getWardId() {
		return wardId;
	}
	public void setWardId(Integer wardId) {
		this.wardId = wardId;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public Integer getGoverningBodyId() {
		return governingBodyId;
	}
	public void setGoverningBodyId(Integer governingBodyId) {
		this.governingBodyId = governingBodyId;
	}
	public String getMpName() {
		return mpName;
	}
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}
	public Integer getGramaPanchayatId() {
		return gramaPanchayatId;
	}
	public void setGramaPanchayatId(Integer gramaPanchayatId) {
		this.gramaPanchayatId = gramaPanchayatId;
	}
	public String getGramaPanchayatName() {
		return gramaPanchayatName;
	}
	public void setGramaPanchayatName(String gramaPanchayatName) {
		this.gramaPanchayatName = gramaPanchayatName;
	}
	public Integer getDistrictInfoId() {
		return districtInfoId;
	}
	public void setDistrictInfoId(Integer districtInfoId) {
		this.districtInfoId = districtInfoId;
	}
	public String getGoveningBodyType() {
		return goveningBodyType;
	}
	public void setGoveningBodyType(String goveningBodyType) {
		this.goveningBodyType = goveningBodyType;
	}	
}
