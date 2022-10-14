package com.example.facilityservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseReportList {
    private int reportNum;
    private String reportTitle;
    private String reportType;
    private LocalDateTime reportDate;
    private Integer reportStatus;
    private String userUuid;
    private String userName;
}
