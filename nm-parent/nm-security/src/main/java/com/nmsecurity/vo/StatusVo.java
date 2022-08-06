package com.nmsecurity.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusVo implements Serializable{

	private static final long serialVersionUID = -4899631259108898808L;
	private String status;
	
	private String error;
	
	@JsonProperty("error_description")	
	private String errorDescription;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
