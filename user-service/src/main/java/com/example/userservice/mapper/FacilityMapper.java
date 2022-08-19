package com.example.userservice.mapper;

import com.example.userservice.dto.FacilityJoinMyDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityMapper {

    String findFacility(String  facilityNo);

    FacilityJoinMyDto facilityJoinMy(FacilityJoinMyDto facilityJoinMyDto);


}
