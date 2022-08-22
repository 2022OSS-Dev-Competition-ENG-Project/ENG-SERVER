package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FacilityContentDto {
    private Integer facilityContentNum;
    private String facilityContentTitle;
    private String facilityContentText;
    private String facilityContentImg;
    private LocalDate facilityContentDate;
    private int facilityContentLook;
    private int facilityContentType;
    private String facilityNo;
    private String userUuid;
}
