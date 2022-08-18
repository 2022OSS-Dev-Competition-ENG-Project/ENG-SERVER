package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityMapper {

    /* 주소로 시설물 상세 정보 불러오기 */
    FacilityDto findDetailFacility(String facilityAddress);

    /* 시설물 등록 */
    void registerFacility(FacilityDto facilityDto);

    /* 이름, 주소로 정보 찾기 */
    FacilityDto nameAndAddressToDto(FacilityDto facilityDto);
}
