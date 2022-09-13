package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityDto;
import com.example.managerservice.dto.ManagerDto;
import com.example.managerservice.vo.FindManagerUuid;
import com.example.managerservice.vo.GetMyFacilityListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityMapper {

    /* 시설물 등록하기 */
    void registerFacility(FacilityDto facilityDto);

    /* 시설물 등록하기 - 매니저의 계정인지 검사하기 */
    Integer validManager(@Param("managerUuid") String managerUuid);

    /* 시설물 등록하기 - 시설물 정보 불러 오기*/
    FacilityDto getFacilityInfo(@Param("facilityAddress") String facilityAddress);

    /* 시설물 가입하기 - 가입된 시설물인지 확인 */
    Integer validConflictFacility(@Param("facilityAddress") String facilityAddress);

    /* 시설물 가입하기 - 매니저 & 사용자 */
    void joinFacility(@Param("uuid") String uuid,
                      @Param("facilityNo") String facilityNo,
                      @Param("table") String table
                      );

    /* 시설물 가입하기 - 존재하는 시설물인지 확인 */
    Integer validFacility(@Param("facilityNo") String facilityNo);

    /* 시설물 가입하기 - 중복된 시설물인지 검사 하기 - Manager & User */
    Integer conflictJoinValidFacility(@Param("facilityNo") String facilityNo,
                                       @Param("userUuid") String userUuid,
                                      @Param("userType") String userType,
                                       @Param("table") String table
                                       );


    /* 시설물 가입하기 - 사용자 확인 */
    Integer joinValidFacility(@Param("userUuid") String userUuid,
                              @Param("userType") String userType,
                              @Param("table") String table);

    /* 내가 가입한 시설물 불러오기 */
    List<GetMyFacilityListVo> getMyFacilityList(
            @Param("uuid") String uuid,
            @Param("table") String table,
            @Param("colum") String colum);


    void deleteMyFacility(@Param("uuid") String uuid,
                            @Param("facilityNo") String facilityNo,
                            @Param("table") String table,
                            @Param("colum") String colum);

    /* 내가 등록한 시설물 좋아요 */
    void myFacilityLike(@Param("userUuid") String userUuid,
                        @Param("useFacility") String useFacility,
                        @Param("value")Integer value);

    /* 내가 등록한 시설물 좋아요 여부 */
    Integer myFacilityLikeBool(@Param("userUuid") String userUuid,@Param("useFacility") String useFacility);

    int validJoinFacility(@Param("uuid") String uuid,
                          @Param("facilityNo") String facilityNo,
                          @Param("table") String table,
                          @Param("colum") String colum);

    /* 시설물 삭제 */
    void deleteFacility(@Param("facilityNo") String facilityNo);

    /* 시설물 삭제 조건 검사 */
    Integer deleteValidFacility(@Param("managerUuid") String managerUuid,
                             @Param("facilityNo")String facilityNo);

    /* 시설물 관리자 지정 */
    void registerManager(@Param("facilityOwner") String facilityOwner,
                         @Param("facilityNo") String facilityNo);

    /* 시설물 가입 - 매니저 UUID 검색 */
    FindManagerUuid findManager(@Param("managerName") String managerName,
                                @Param("managerPhoneNumber") String managerPhoneNumber);

    /* 시설물 별 관리자 리스트 */
    List<ManagerDto> facilityManagerList(@Param("facilityNo") String facilityNo);
}
