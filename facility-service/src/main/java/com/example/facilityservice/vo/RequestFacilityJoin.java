package com.example.facilityservice.vo;

import lombok.Data;

@Data
public class RequestFacilityJoin {

    private String facilityNum;

    /* Manager 또는 User UUID */
    private String uuid;

}
