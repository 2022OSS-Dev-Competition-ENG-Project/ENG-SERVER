package com.example.facilityservice.mapper;

import com.example.facilityservice.vo.ResponseFacilityManagerList;
import com.example.facilityservice.vo.ResponseGetMyFacility;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Mapper
public interface FacilityJoinMapper {

    /* 시설물 가입 */
    void joinFacilityUser(@Param("facilityNum") String facilityNum,
                          @Param("uuid") String uuid,
                          @Param("table") String table);


    /* 시설물 가입 */
    void joinFacilityManager(@Param("facilityNum") String facilityNum,
                            @Param("uuid") String uuid,
                            @Param("table") String table);

    /* 시설물 가입 - 중복 가입 검사 */
    Integer conflictValidJoin(@Param("facilityNum") String facilityNum,
                              @Param("uuid") String uuid,
                              @Param("table") String table,
                              @Param("colum") String colum);

    /* 시설물 탈퇴*/
    void resignationFacility(@Param("facilityNum") String facilityNum,
                             @Param("uuid") String uuid,
                             @Param("table") String table,
                             @Param("colum") String colum);

    /* 시설물 탈퇴 - 사용자 존재 여부 확인 */
    Integer isValidJoin (@Param("facilityNum") String facilityNum,
                         @Param("uuid") String uuid,
                         @Param("table") String table,
                         @Param("colum") String colum);

    /* 내가 가입한 시설물 불러오기 ( Manager ) */
    List<ResponseGetMyFacility> getMyFacilityListManager(@Param("uuid") String uuid,
                                                         @Param("table") String table,
                                                         @Param("colum") String colum);

    /* 내가 가입한 시설물 불러오기 ( User ) */
    List<ResponseGetMyFacility> getMyFacilityListUser(@Param("uuid") String uuid,
                                                      @Param("table") String table,
                                                      @Param("colum") String colum);

    /* 내가 가입한 시설 좋아요 */
    void updateFacilityLike(@Param("userUuid") String userUuid,
                            @Param("facilityNum") String facilityNum,
                            @Param("bool") int bool);

    /* 내가 가입한 시설 좋아요 - 좋아요 여부 */
    Integer facilityLikeBool(@Param("userUuid") String userUuid,
                             @Param("facilityNum") String facilityNum);


    /* 시설물 가입 - 매니저 검색 ( OpenFeign ) */
    String findJoinManager(@Param("managerName") String managerName,
                           @Param("managerPhoneNumber") String managerPhoneNumber);

    /* 시설물에 가입된 매니저 불러오기 */
    List<ResponseFacilityManagerList> getFacilityManagerList(@Param("facilityNum") String facilityNum);

    /* 시설물에 가입된 매니저 삭제 - 매니저 직급 불러오기 */
    String getManagerGrade(@Param("managerUuid") String managerUuid,
                         @Param("facilityNum") String facilityNum);

    /* 시설물에 가입된 매니저 삭제 */
    void joinDeleteManager(@Param("managerUuid") String managerUuid,
                                     @Param("facilityNum") String facilityNum);
}
