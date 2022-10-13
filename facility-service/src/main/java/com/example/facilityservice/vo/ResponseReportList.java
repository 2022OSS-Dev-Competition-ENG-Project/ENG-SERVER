package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseReportList {
    private int reportNum;
    private String reportTitle;
    private String reportType;
    private LocalDateTime reportDate;
    private Integer reportStatus;
    private String userUuid;
    private String userName;
}
