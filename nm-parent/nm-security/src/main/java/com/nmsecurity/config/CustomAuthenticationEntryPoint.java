package com.nmsecurity.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmcommon.constants.AppConstants;
import com.nmcommon.vo.ErrorResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{	
	
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {					
		ErrorResponse errRes = new ErrorResponse();
		errRes.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
		errRes.setErrorMsg("Unauthorized");
		errRes.setStatus(AppConstants.STATUS_FAILED);		
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(errRes);
        response.getWriter().write(jsonStr);
        response.setStatus(HttpStatus.OK.value());        
	}
}
