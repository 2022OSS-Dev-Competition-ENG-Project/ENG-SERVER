package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.vo.RequestContentLike;
import com.example.facilityservice.vo.ResponseContentDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityContentMapper {

    /* 게시물 생성*/
    void createContent(FacilityContent facilityContent);

    /* 게시물 상세 보기 */
    ResponseContentDetail viewContentDetail(@Param("userUuid") String userUuid,
                                            @Param("contentNum") Integer contentNum);

    /* 게시물 삭제*/
    void deleteContent(@Param("contentNum") Integer contentNum);

    /* 게시물 삭제 - 시설에 속해 있는지 또는 매니저인지 검증 */
    Integer validJoinFacility(@Param("managerUuid") String managerUuid);

    /* 게시물 삭제 - 작성자 인지 검증 */
    Integer validContentWriter(@Param("userUuid") String userUuid,
                               @Param("contentNum") Integer contentNum);

    /* 게시물 좋아요 */
    void facilityContentLike(@Param("userUuid") String userUuid,
                             @Param("contentNum") Integer contentNum);

    /* 게시물 좋아요 취소*/
    void deleteContentLike(@Param("userUuid") String userUuid,
                           @Param("contentNum") Integer contentNum);

    /* 게시물 좋아요 - 여부*/
    Integer contentLikeBool(@Param("userUuid") String userUuid,
                            @Param("contentNum") Integer contentNum);

    /* 게시물 좋아요 개수 */
    Integer getContentLikeCount(@Param("contentNum") Integer contentNum);


    /* 게시물 리스트 불러오기 - ALL */
    List<FacilityContent> getContentList(@Param("facilityNum") String facilityNum);

    /* 게시물 리스트 불러오기 - Main Banner(5) */
    List<FacilityContent> getContentListLt(@Param("facilityNum") String facilityNum,
                                           @Param("count") Integer count);

    /* 게시물 총 개수 불러오기 */
    Integer getContentCount(String facilityNum);

    /* 내가 쓴 게시물 불러오기 - ALL */
    List<FacilityContent> getMyContent(@Param("userUuid") String userUuid);

    /* 내가 쓴 게시물 불러오기 - Main */
    List<FacilityContent> getMyContentMain(@Param("userUuid") String userUuid,
                                           @Param("count") Integer count);
}
