package com.linko.linko.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig implements CachingConfigurer {
    @Override
    public CacheErrorHandler errorHandler(){
        return new CustomCacheErrorHandler();
    }
}