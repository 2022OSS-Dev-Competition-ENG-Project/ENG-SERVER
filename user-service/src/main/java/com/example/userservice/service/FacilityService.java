package com.example.userservice.service;

import com.example.userservice.dto.FacilityJoinMyDto;
import com.example.userservice.mapper.FacilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacilityService {

    private FacilityMapper facilityMapper;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    public String findFacility(String facilityNo){
        return facilityMapper.findFacility(facilityNo);
    }

    public FacilityJoinMyDto facilityJoinMy(FacilityJoinMyDto facilityJoinMyDto){
        return facilityMapper.facilityJoinMy(facilityJoinMyDto);
    }
}
