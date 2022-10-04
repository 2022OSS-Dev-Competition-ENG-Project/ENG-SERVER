package com.example.facilityservice.mapper;

import com.example.facilityservice.vo.RequestFacilityJoin;
import com.example.facilityservice.vo.RequestFacilityRegister;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityMapper {

    /* 시설물 생성 */
    void registerFacility(RequestFacilityRegister facility);

    /* 시설물 생성 - 주소 중복 검사 */
    Integer conflictValidAddress(String facilityAddress);

    /* 시설물 가입 */
    void joinFacility(RequestFacilityJoin requestFacilityJoin);

}
