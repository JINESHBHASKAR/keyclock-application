package com.nmsecurity.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config-details")
public class AppConfig implements Serializable {
	private static final long serialVersionUID = -8626021502310963045L;
	private Map<String, String> cashTransaction;
	private Map<Integer,String> roleList;
	private Map<String, String> errorCode;
	private Map<String, List<String>> roleGrantLevel;
	private Map<String, String> contentMessage;	
	private List<String> district;
	private List<String> state;	
	public Map<String, String> getCashTransaction() {
		return cashTransaction;
	}
	public void setCashTransaction(Map<String, String> cashTransaction) {
		this.cashTransaction = cashTransaction;
	}

	public Map<Integer, String> getRoleList() {
		return roleList;
	}

	public void setRoleList(Map<Integer, String> roleList) {
		this.roleList = roleList;
	}

	public Map<String, String> getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Map<String, String> errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, List<String>> getRoleGrantLevel() {
		return roleGrantLevel;
	}

	public void setRoleGrantLevel(Map<String, List<String>> roleGrantLevel) {
		this.roleGrantLevel = roleGrantLevel;
	}

	public Map<String, String> getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(Map<String, String> contentMessage) {
		this.contentMessage = contentMessage;
	}

	public List<String> getDistrict() {
		return district;
	}

	public void setDistrict(List<String> district) {
		this.district = district;
	}

	public List<String> getState() {
		return state;
	}

	public void setState(List<String> state) {
		this.state = state;
	}		
}
