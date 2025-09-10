package com.linko.linko.service;


import com.linko.linko.models.ClickEvent;
import com.linko.linko.models.UrlMapping;
import com.linko.linko.repository.ClickEventRepo;
import com.linko.linko.repository.UrlMappingRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@EnableAsync
@AllArgsConstructor
public class AsyncAnalyticsService {

    private UrlMappingRepo urlMappingRepo;
    private ClickEventRepo clickEventRepo;

    @Async
    public void trackClick(String shortUrl) {
        int updatedRows = urlMappingRepo.incrementClickCount(shortUrl);
        if (updatedRows > 0) {
            UrlMapping urlMapping = urlMappingRepo.findByShortUrl(shortUrl);
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setUrlMapping(urlMapping); // Link to the parent entity
            clickEvent.setClickDate(LocalDateTime.now());
            clickEventRepo.save(clickEvent);
        }
    }
}

