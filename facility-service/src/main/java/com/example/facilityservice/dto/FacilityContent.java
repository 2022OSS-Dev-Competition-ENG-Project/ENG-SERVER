package com.example.facilityservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityContent {
    private Integer contentNum;
    private Integer facilityJoinNum;
    private String contentTitle;
    private String contentText;
    private int contentLook;
    private LocalDateTime contentDate;
    private Integer contentLike;
}
