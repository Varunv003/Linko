package com.linko.linko.service;


import com.linko.linko.dto.UrlMappingDTO;
import com.linko.linko.mapper.UrlMappingMapper;
import com.linko.linko.models.UrlMapping;
import com.linko.linko.repository.UrlMappingRepo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlMappingCacheService {

    private UrlMappingRepo urlMappingRepo;
    private UrlMappingMapper urlMappingMapper;

    @Cacheable(value = "shortUrls", key = "#shortUrl")
    public UrlMappingDTO findByShortUrl(String shortUrl){
        UrlMapping entity = urlMappingRepo.findByShortUrl(shortUrl);
        return entity != null ? urlMappingMapper.convertToUrlMappingDto(entity) : null;
    }


}
