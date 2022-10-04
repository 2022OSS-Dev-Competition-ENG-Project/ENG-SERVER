package com.example.facilityservice.vo;

import lombok.Data;

@Data
public class RequestFacilityRegister {

    /* 매니저 고유 번호 */
    private String managerId;

    /* 시설물 번호 */
    private String facilityNum;

    /* 시설물 이름 */
    private String facilityName;

    /* 시설물 주소 */
    private String facilityAddress;

    /* 시설물 QR*/
    private String facilityQr;
}
