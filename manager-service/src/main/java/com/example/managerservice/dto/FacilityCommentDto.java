package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityCommentDto {
    private Integer commentNum;
    private String commentText;
    private Integer commentLike;
    private Integer commentDisLike;
    private LocalDateTime commentDate;
    private Integer contentNum;
    private String userUuid;
    private String facilityUuid;
}
