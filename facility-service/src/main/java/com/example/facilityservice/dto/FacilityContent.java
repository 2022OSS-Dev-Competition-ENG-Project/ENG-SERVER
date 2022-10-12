package com.example.facilityservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacilityContent {
    private Integer contentNum;
    private String userUuid;
    private String facilityNum;
    private String contentTitle;
    private String contentText;
    private int contentLook;
    private LocalDateTime contentDate;
}
