package com.example.facilityservice;

import lombok.Data;

@Data
public class Facility {

    /* 시설물 번호 */
    private String facilityNum;

    /* 시설물 이름 */
    private String facilityName;

    /* 시설물 주소 */
    private String facilityAddress;

    /* 시설물 QR*/
    private String facilityQr;

}
