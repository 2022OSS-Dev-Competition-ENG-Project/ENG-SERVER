package com.example.managerservice.vo;

import lombok.Data;

@Data
public class ContentCommentVo {
    private Integer contentCommentNum;
    private String contentCommentText;
    private Integer contentCommentLike;
    private Integer contentCommentDisLike;
    private Integer facilityContentNum;
    private String userName;
    private String FacilityName;

}
