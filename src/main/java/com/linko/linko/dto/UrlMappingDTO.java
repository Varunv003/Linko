package com.linko.linko.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlMappingDTO{
    private Long id;

    @JsonProperty("original_url")
    private String originalUrl;

    @JsonProperty("short_url")
    private String shortUrl;

    @JsonProperty("click_count")
    private int clickCount;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    private String username;
}
