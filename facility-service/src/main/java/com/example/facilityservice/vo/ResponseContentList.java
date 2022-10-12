package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseContentList {

    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private LocalDateTime contentDate;
    private Integer contentLook;
    private String writerNickName;
    private String writerName;
    private String writerProfileImg;
    private String writerUuid;
    private Integer userLikeBool;

}
