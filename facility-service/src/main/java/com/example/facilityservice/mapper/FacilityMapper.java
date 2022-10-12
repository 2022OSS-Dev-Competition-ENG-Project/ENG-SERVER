package com.example.facilityservice.mapper;

import com.example.facilityservice.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FacilityMapper {

    /* 시설물 생성 */
    void registerFacility(RequestFacilityRegister facility);

    /* 시설물 생성 - 시설물 가입 */
    void rJoinFacility(RequestFacilityRegister facility);

    /* 시설물 생성, 시설물 수정 - 주소 중복 검사 */
    Integer conflictValidAddress(@Param("facilityAddress") String facilityAddress);

    /* 시설물 생성 - 최초 등급 설정*/
    void setGrade(@Param("managerUuid") String managerUuid,@Param("grade") String grade);

    /* 시설물 수정 - 시설물 이름 */
    void facilityChangeName(RequestFacilityChangeName changeData);

    /* 시설물 수정 - 시설물 이름 중복 검사*/
    Integer conflictValidName(@Param("facilityName") String facilityName);

    /* 시설물 수정 - 시설물 주소 */
    void facilityChangeAddress(RequestChangeAddress changeData);

    /* 시설물 삭제 */
    void deleteFacility(RequestDeleteFacility deleteFacility);

    /* 시설물 삭제 - 관리자 검증 */
    Integer validManagerGrade(@Param("facilityNum") String facilityNum,
                              @Param("managerUuid") String managerUuid);
}
