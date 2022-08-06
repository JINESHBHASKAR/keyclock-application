package com.nmsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nmcommon.exception.BusinessException;

@RestController
public class AuthController {
	private final static Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/test")
    public String getTest() {
        return "testApi";
    }
	
	@GetMapping("/error")
    public String getErrorMsg() {
        throw new BusinessException("not authenticated");
    }
}
