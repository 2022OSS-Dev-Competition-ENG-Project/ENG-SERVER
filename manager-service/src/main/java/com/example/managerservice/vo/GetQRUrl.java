package com.example.managerservice.vo;

import lombok.Data;

@Data
public class GetQRUrl {
    private String facilityName;
    private String facilityAddress;
    private String facilityQRUrl;
}
