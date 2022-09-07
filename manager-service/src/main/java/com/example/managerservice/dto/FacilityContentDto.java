package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityContentDto {

    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private String contentImg;
    private LocalDateTime contentDate;
    private int contentLook;
    private int contentType;
    private String facilityNo;
    private String userUuid;
}
