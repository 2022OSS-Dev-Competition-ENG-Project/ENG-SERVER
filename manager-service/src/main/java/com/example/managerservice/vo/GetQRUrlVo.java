package com.example.managerservice.vo;

import lombok.Data;

@Data
public class GetQRUrlVo {
    private String facilityName;
    private String facilityAddress;
    private String facilityQRUrl;
}
