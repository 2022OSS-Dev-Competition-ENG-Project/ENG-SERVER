package com.example.facilityservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityReport {
    private int reportNum;
    private String reportTitle;
    private String reportText;
    private String reportType;
    private LocalDateTime reportDate;
    private String reportImg;
    private String reportStatus;
    private String userUuid;
    private String facilityNum;
}
