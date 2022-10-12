package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCommentList {
    private Integer commentNum;
    private String commentText;
    private LocalDateTime commentDate;
    private String userUuid;
    private String userName;
}
