package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.dto.FacilityJoinDto;
import com.example.managerservice.dto.FacilityListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityMapper {

    /* 주소로 시설물 상세 정보 불러오기 */
    FacilityDto findDetailFacilityAd(String facilityAddress);

    /* 시설물 등록 */
    void registerFacility(FacilityDto facilityDto);

    /* 이름, 주소로 정보 찾기 */
    FacilityDto nameAndAddressToDto(FacilityDto facilityDto);

    /* 시설 아이디로 시설 등록 중복 검증 */
    Integer findDetailFacilityId(String userUuid, String userFacility);

    /* 시설에 사용자 추가*/
    void facilityUserJoin(FacilityJoinDto facilityJoinDto);

    /* 시설물 번호로 시설물 찾기*/
    FacilityDto findDetailFacilityFn(String facilityNo);

    /* 내가 등록한 시설물 리스트 불러오기 */
    List<FacilityDto> getMyFacilityList(String userUuid);
}
