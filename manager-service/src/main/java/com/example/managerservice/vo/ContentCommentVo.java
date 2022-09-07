package com.example.managerservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentCommentVo {
    private Integer commentNum;
    private String commentText;
    private LocalDateTime commentDate;
    private String userUuid;
    private String userNickName;
}
