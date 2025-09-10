package com.linko.linko.config;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class FilterConfig {
    @Bean
    public Bucket rateLimitBucket() {
        Bandwidth limit = Bandwidth.simple(10, Duration.ofMinutes(1));
        return Bucket.builder().addLimit(limit).build();
    }
}
