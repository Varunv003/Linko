package com.linko.linko.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClickEventDTO {

    @JsonProperty("click_date")
    private LocalDate clickDate;
    private Long count;
}
