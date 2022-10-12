package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseNoticeDetail {
    private String writerNickName;
    private String writerUuid;
    private Integer noticeNum;
    private String noticeTitle;
    private String noticeText;
    private LocalDateTime noticeDate;
    private Integer noticeLook;
    private String noticeImages;
}
