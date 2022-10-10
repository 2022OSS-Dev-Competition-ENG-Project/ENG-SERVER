package com.example.facilityservice.mapper;

import com.example.facilityservice.vo.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityMapper {

    /* 시설물 생성 */
    void registerFacility(RequestFacilityRegister facility);

    /* 시설물 생성 - 주소 중복 검사 */
    Integer conflictValidAddress(String facilityAddress);

    /* 시설물 수정 - 시설물 이름 */
    void facilityChangeName(RequestFacilityChangeName changeData);

    /* 시설물 수정 - 시설물 주소 */
    void facilityChangeAddress(RequestChangeAddress changeData);

    /* 시설물 삭제 */
    void deleteFacility(RequestDeleteFacility deleteFacility);
}
