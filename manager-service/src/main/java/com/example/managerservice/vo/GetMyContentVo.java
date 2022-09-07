package com.example.managerservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetMyContentVo {
    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private LocalDateTime contentDate;
}
