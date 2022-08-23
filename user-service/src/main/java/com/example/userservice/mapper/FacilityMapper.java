package com.example.userservice.mapper;

import com.example.userservice.dto.FacilityJoinMyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityMapper {

    String findFacility(String  facilityNo);

    FacilityJoinMyDto facilityJoinMy(FacilityJoinMyDto facilityJoinMyDto);

    List getMyFacility(String uuid);


}
