package com.company.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class ERPApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ERPApplication.class, args);
    }
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("employees", "departments", "products", "customers");
    }
}
