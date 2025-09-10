package com.linko.linko.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    private final StringRedisTemplate redisTemplate;

    public RedisTestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis-test")
    public String testRedis() {
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            return "Redis connection successful!";
        } catch (Exception e) {
            return "Redis connection failed: " + e.getMessage();
        }
    }
}
