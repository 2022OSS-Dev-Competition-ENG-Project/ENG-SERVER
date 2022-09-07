package com.example.managerservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentListVo {

    private Integer contentNum;
    private String contentTitle;
    private String contentText;
    private String contentImg;
    private LocalDateTime contentDate;
    private String name;

}
