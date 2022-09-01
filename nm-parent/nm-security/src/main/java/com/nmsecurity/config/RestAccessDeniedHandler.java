package com.nmsecurity.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmcommon.constants.AppConstants;
import com.nmcommon.vo.ErrorResponse;

/**
 * 
 * @author Jinesh KP
 *
 */

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {		        
        ErrorResponse errRes = new ErrorResponse();
		errRes.setErrorCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
		errRes.setErrorMsg("Access Denied");
		errRes.setStatus(AppConstants.STATUS_FAILED);
		ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(errRes);
        response.getWriter().write(jsonStr);
	}

}
