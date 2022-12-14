package com.example.facilityservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityNotice {
    private Integer noticeNum;
    private String managerUuid;
    private String facilityNum;
    private String noticeTitle;
    private String noticeText;
    private String noticeImages;
    private int noticeLook;
    private LocalDateTime noticeDate;
}
