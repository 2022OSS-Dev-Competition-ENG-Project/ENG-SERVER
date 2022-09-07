package com.example.managerservice.vo;

import lombok.Data;

@Data
public class GetMyFacilityListVo {
    private String uuid;
    private String name;
    private String facilityAddress;
    private String useFacility;
    private String facilityName;
    private String facilityOwner;
    private int isLiked;
}
