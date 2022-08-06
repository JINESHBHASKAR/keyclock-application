package com.nmcommon.exception;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = -6692549826477378983L;
	private String errorCode;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String errorMsg) {
		super(errorMsg);
	}
	public BusinessException(String errorMsg,String errorCode) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
