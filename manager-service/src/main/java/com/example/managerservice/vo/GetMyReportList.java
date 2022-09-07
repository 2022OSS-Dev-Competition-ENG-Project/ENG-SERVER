package com.example.managerservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMyReportList {
    private Integer reportNum;
    private String reportTitle;
    private String reportText;
    private String reportType;
    private LocalDateTime reportDate;
    private String reportImg;
    private String reportStatus;
    private String userUuid;
    private String userNickname;
    private String facilityNo;
}
