package com.linko.linko.mapper;

import com.linko.linko.dto.UrlMappingDTO;
import com.linko.linko.models.UrlMapping;
import org.springframework.stereotype.Component;

@Component
public class UrlMappingMapper {

    public UrlMappingDTO convertToUrlMappingDto(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        return urlMappingDTO;
    }

}
