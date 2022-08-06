package com.nmcommon.vo;

import java.io.Serializable;

public class ErrorResponse implements Serializable{
	
	private static final long serialVersionUID = -4033937348246299745L;
	private String status;
	private String errorMsg;
	private String errorCode;	
	
	public ErrorResponse() {				
	}
	
	public ErrorResponse(Exception e) {		
		this.errorMsg = e.getMessage();
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
