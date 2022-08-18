package com.example.managerservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FacilityContent {
    private String FacilityId;
    private String FacilityContentNum;
    private int FacilityContentType;
    private String FacilityContentTitle;
    private String FacilityContentText;
    private int FacilityContentLook;
    private LocalDate FacilityContentDate;
}
