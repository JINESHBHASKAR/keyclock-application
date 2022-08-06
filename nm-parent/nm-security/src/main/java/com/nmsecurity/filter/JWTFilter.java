package com.nmsecurity.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JWTFilter implements Filter{

	private final static Logger LOG = LoggerFactory.getLogger(JWTFilter.class);

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {		
		HttpServletRequest req = (HttpServletRequest) request;
		LOG.info("Starting Transaction for req :{}", req.getRequestURI());
		String uri = req.getRequestURI();
		
		chain.doFilter(request, response);
		LOG.info("Committing Transaction for req :{}", req.getRequestURI());
	}

}
