package com.linko.linko.service;

import com.linko.linko.dto.ClickEventDTO;
import com.linko.linko.dto.UrlMappingDTO;
import com.linko.linko.mapper.UrlMappingMapper;
import com.linko.linko.models.ClickEvent;
import com.linko.linko.models.UrlMapping;
import com.linko.linko.models.User;
import com.linko.linko.repository.ClickEventRepo;
import com.linko.linko.repository.UrlMappingRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private UrlMappingRepo urlMappingRepo;
    private ClickEventRepo clickEventRepo;
    private UrlMappingCacheService urlMappingCacheService;
    private UrlMappingMapper urlMappingMapper;
    private AsyncAnalyticsService asyncAnalyticsService;


    public UrlMappingDTO createShortUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        UrlMapping savedUrlMapping = urlMappingRepo.save(urlMapping);
        return urlMappingMapper.convertToUrlMappingDto(savedUrlMapping);
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepo.findByUser(user).stream()
                .map(urlMapping -> urlMappingMapper.convertToUrlMappingDto(urlMapping))
                .toList();
    }

//    @Cacheable("shortUrls")
//    public UrlMapping findByShortUrl(String shortUrl){
//        return urlMappingRepo.findByShortUrl(shortUrl);
//    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end){
        UrlMapping urlMapping = urlMappingRepo.findByShortUrl(shortUrl);

        if(urlMapping != null){
            return clickEventRepo.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    }).collect(Collectors.toList());
        }

        return null;
    }

    public Map<LocalDate, Long> getTotalClickByUserAndDate(User user, LocalDate start, LocalDate end){
        List<UrlMapping> urlMappings = urlMappingRepo.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepo.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());

        return clickEvents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
    }


    public UrlMappingDTO getOriginalUrl(String shortUrl){
        UrlMappingDTO urlMappingDTO = urlMappingCacheService.findByShortUrl(shortUrl);

        if(urlMappingDTO != null){
            asyncAnalyticsService.trackClick(shortUrl);
            return urlMappingDTO;
        }

        return null;


    }

//    @CacheEvict(value = "shortUrls", key = "#shortUrl")
//    public void evictShortUrlCache(String shortUrl) {}


    public UrlMappingDTO updateOriginalUrl(String shortUrl, String newOriginalUrl, User user) {
        UrlMapping urlMapping = urlMappingRepo.findByShortUrl(shortUrl);
        if (urlMapping != null && urlMapping.getUser().getUsername().equals(user.getUsername())) {
            urlMapping.setOriginalUrl(newOriginalUrl);
            urlMappingRepo.save(urlMapping);

//            evictShortUrlCache(shortUrl);

            return urlMappingMapper.convertToUrlMappingDto(urlMapping);
        }

        return null;
    }



    @Transactional
    public boolean deleteShortUrl(String shortUrl, User user) {
        UrlMapping urlMapping = urlMappingRepo.findByShortUrl(shortUrl);
        if (urlMapping != null && urlMapping.getUser().getUsername().equals(user.getUsername())) {
            urlMappingRepo.delete(urlMapping);

//            evictShortUrlCache(shortUrl);

            return true;
        }

        return false;
    }

}
