package com.linko.linko.controller;

import com.linko.linko.dto.UrlMappingDTO;
import com.linko.linko.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {
    private UrlMappingService urlMappingService;

    @GetMapping("/r/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.print("Current authentication: " + auth);
        UrlMappingDTO urlMapping = urlMappingService.getOriginalUrl(shortUrl);


        if(urlMapping != null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", urlMapping.getOriginalUrl());

            return ResponseEntity.status(302).headers(httpHeaders).build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

}
