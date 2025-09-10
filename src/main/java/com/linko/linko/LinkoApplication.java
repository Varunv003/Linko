package com.linko.linko;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class LinkoApplication {

	@Bean
	public CommandLineRunner checkCache(CacheManager cacheManager) {
		return args -> System.out.println(">>> Cache Manager: " + cacheManager.getClass().getName());
	}


	public static void main(String[] args) {
		SpringApplication.run(LinkoApplication.class, args);
	}

}
