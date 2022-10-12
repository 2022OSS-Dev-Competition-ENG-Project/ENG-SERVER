package com.example.facilityservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityComment {
    private Integer commentNum;
    private String commentText;
    private LocalDateTime commentDate;
    private Integer contentNum;
    private String userUuid;
}
