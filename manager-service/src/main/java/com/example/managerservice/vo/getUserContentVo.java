package com.example.managerservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class getUserContentVo {
    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private String contentImg;
    private LocalDateTime contentDate;
    private String contentLook;
    private String writerUuid;
    private String writerNickname;
    private String writerImage;
    private int userLikeBool;

}
