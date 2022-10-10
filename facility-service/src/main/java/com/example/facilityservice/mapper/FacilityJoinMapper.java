package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.Facility;
import com.example.facilityservice.vo.RequestJoinFacility;
import com.example.facilityservice.vo.RequestResignationFacility;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityJoinMapper {

    /* 시설물 가입 */
    void joinFacilityUser(RequestJoinFacility joinFacility, @Param("table") String table);

    /* 시설물 가입 - 중복 가입 검사 */
    Integer conflictJoin(RequestJoinFacility joinFacility);

    /* 시설물 탈퇴*/
    void resignationFacility(RequestResignationFacility resignationFacility,@Param("table") String table);

    /* 내가 가입한 시설물 불러오기*/
    List<Facility> getManagerFacilityList(@Param("uuid") String uuid,
                                          @Param("table") String table);

    /* 내가 가입한 시설 좋아요 */
    void updateFacilityLike(@Param("userUuid") String userUuid,
                            @Param("facilityNum") String facilityNum,
                            @Param("i") int i);

    /* 내가 가입한 시설 좋아요 - 좋아요 여부 */
    Integer facilityLikeBool(@Param("userUuid") String userUuid,
                             @Param("facilityNum") String facilityNum);


}
