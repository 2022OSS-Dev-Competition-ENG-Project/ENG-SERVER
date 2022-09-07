package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private int reportNum;
    private String reportTitle;
    private String reportText;
    private String reportType;
    private LocalDateTime reportDate;
    private String reportImg;
    private String reportStatus;
    private String userUuid;
    private String facilityNo;
}
