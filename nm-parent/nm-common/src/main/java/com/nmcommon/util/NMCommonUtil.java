package com.nmcommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NMCommonUtil {
	private static final Logger log = LoggerFactory.getLogger(NMCommonUtil.class);
	
	public void testMethod() {
		log.info("inside testMethod method - CommonUtil");
		System.out.println("================================Testing Console=====================================");
	}
	
}
