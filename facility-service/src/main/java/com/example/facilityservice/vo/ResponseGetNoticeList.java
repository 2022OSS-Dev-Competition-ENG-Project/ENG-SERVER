package com.example.facilityservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseGetNoticeList {
    private Integer noticeNum;
    private String managerUuid;
    private String facilityNum;
    private String noticeTitle;
    private String noticeText;
    private String noticeImages;
    private int noticeLook;
    private LocalDateTime noticeDate;
    private String writerName;
    private String writerUuid;
    private String writerNickName;
}
