package com.example.facilityservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGetMyFacility {
    private String facilityNum;
    private String facilityName;
    private String facilityOwner;
    private String facilityOwnerName;
    private String facilityAddress;
    private Integer likeBool;
}
