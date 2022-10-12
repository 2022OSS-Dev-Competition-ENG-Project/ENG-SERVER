package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseContentDetail {

    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private LocalDateTime contentDate;
    private Integer contentLook;
    private String writerNickName;
    private String writerProfileImg;
    private String writerUuid;
    private Integer userLikeBool;

}
