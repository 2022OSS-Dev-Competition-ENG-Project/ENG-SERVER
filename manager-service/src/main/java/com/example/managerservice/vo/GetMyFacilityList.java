package com.example.managerservice.vo;

import lombok.Data;

@Data
public class GetMyFacilityList {
    private String userUuid;
    private String facilityAddress;
    private String userFacility;
    private String facilityName;
    private int isLiked;
}
