package com.example.managerservice.service;

import com.example.managerservice.constant.RegisterConstant;
import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.mapper.FacilityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FacilityService {

    private FacilityMapper facilityMapper;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    /* 시설물 등록 */
    public String registerFacility(FacilityDto facilityDto) {
        facilityMapper.registerFacility(facilityDto);
        return RegisterConstant.REGISTER_SUCCESS;
    }

    /* 주소로 시서물 상제 정보 찾기 */
    public String findDetailFacility(String facilityAddress) {
        FacilityDto facilityDto;
        facilityDto = facilityMapper.findDetailFacility(facilityAddress);
        return facilityDto.getFacilityAddress();
    }

    /* 이름, 주소로 정보 찾기 */
    public FacilityDto nameAndAddressToDto(FacilityDto facilityDto){


        FacilityDto fa = new FacilityDto();
        fa = facilityMapper.nameAndAddressToDto(facilityDto);
        log.info(fa.toString());
        return facilityMapper.nameAndAddressToDto(facilityDto);
    }
}
