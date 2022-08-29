package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FacilityContentCommentDto {
    private Integer contentCommentNum;
    private String contentCommentText;
    private LocalDate contentCommentDate;
    private String contentCommentFacility;
    private String contentCommentUser;
    private Integer facilityContentNum;

}
