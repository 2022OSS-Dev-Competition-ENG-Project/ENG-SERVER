package com.example.managerservice.dto;

import lombok.Data;

@Data
public class FacilityJoinDto {

    /* 매니저와 사용자가 같은 쓰는 데이터이기 때문에 따로 나누지 않았습니다. */
    private String uuid;
    private String facilityNo;
}
