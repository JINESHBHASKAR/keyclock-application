package com.nmsecurity.vo;

import java.util.List;
import java.util.Map;

import com.nmsecurity.model.StatusVo;

/**
 * 
 * @author Jinesh KP
 *
 */

public class ConfigVo extends StatusVo{

	private static final long serialVersionUID = -662127501981043717L;
	private Map<String, String> cashTransaction;
	private Map<String,String> errorCode;
	private Map<String,String> contentMessage;
	private Map<Integer,String> wardNameMap;
	private Integer wardId;
	private String wardName;
	private List<String> districtNames;
	private List<String> stateNames;
	private Boolean forceUpdate;
	
	public Map<String, String> getCashTransaction() {
		return cashTransaction;
	}
	public void setCashTransaction(Map<String, String> cashTransaction) {
		this.cashTransaction = cashTransaction;
	}
	public Map<String, String> getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Map<String, String> errorCode) {
		this.errorCode = errorCode;
	}
	public Map<String, String> getContentMessage() {
		return contentMessage;
	}
	public void setContentMessage(Map<String, String> contentMessage) {
		this.contentMessage = contentMessage;
	}
	public Map<Integer, String> getWardNameMap() {
		return wardNameMap;
	}
	public void setWardNameMap(Map<Integer, String> wardNameMap) {
		this.wardNameMap = wardNameMap;
	}
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
	public final List<String> getDistrictNames() {
		return districtNames;
	}
	public final void setDistrictNames(List<String> districtNames) {
		this.districtNames = districtNames;
	}
	public final List<String> getStateNames() {
		return stateNames;
	}
	public final void setStateNames(List<String> stateNames) {
		this.stateNames = stateNames;
	}
	public Boolean getForceUpdate() {
		return forceUpdate;
	}
	public void setForceUpdate(Boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}	
}
