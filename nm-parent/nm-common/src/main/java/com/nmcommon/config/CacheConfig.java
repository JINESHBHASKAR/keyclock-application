package com.nmcommon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
private static final Logger log = LoggerFactory.getLogger(CacheConfig.class); 	
	
	@Bean
	public org.springframework.cache.CacheManager cacheManager() {
		log.info("inside cacheManager CacheConfig");
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
		cacheManager.setAllowNullValues(false);
		cacheManager.setStoreByValue(true);		
		TransactionAwareCacheManagerProxy transactionAwareCacheManagerProxy=new TransactionAwareCacheManagerProxy(cacheManager);		
		return transactionAwareCacheManagerProxy;
	}
}
