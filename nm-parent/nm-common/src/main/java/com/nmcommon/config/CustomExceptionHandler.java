package com.nmcommon.config;



import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nmcommon.constants.AppConstants;
import com.nmcommon.exception.BusinessException;
import com.nmcommon.vo.ErrorResponse;


@ControllerAdvice
public class CustomExceptionHandler{
	Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleCommonException(final Exception exception) {
		log.info("inside handleCommonException CustomExceptionHandler");
		ErrorResponse error = new ErrorResponse();
		error.setErrorMsg(exception.getMessage());
		error.setStatus(AppConstants.STATUS_FAILED);
		error.setErrorCode("");		
		return error;
	}
	
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponse handleBusinessException(final BusinessException exception) {
		log.info("inside handleBusinessException CustomExceptionHandler");
		ErrorResponse error = new ErrorResponse();
		error.setErrorMsg(exception.getMessage());
		error.setStatus(AppConstants.STATUS_FAILED);
		error.setErrorCode(StringUtils.isNotBlank(exception.getErrorCode())?exception.getErrorCode():"");		
		return error;
	} 
}
