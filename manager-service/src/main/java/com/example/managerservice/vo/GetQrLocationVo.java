package com.example.managerservice.vo;

import lombok.Data;

@Data
public class GetQrLocationVo {
    private String facilityName;
    private String facilityAddress;
    private String facilityQRUrl;
}
