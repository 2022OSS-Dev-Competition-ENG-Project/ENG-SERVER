package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.vo.RequestContentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityContentMapper {

    /* 게시물 생성*/
    void createContent(FacilityContent facilityContent);

    /* 게시물 상세 보기*/
    FacilityContent viewContentDetail(@Param("facilityJoinNum") Integer facilityJoinNum,
                                      @Param("contentId") Integer contentId);

    /* 공지 생성 */
    long createNotice(FacilityContent facilityContent);

    /* 공지 생성 - 이미지 업데이트 */
    void contentImageUpdate(String saveDbPath, Integer contentNum);

    FacilityNotice viewNoticeDetail(Integer facilityJoinNum, Integer noticeId);

    /* 게시물 삭제*/
    void deleteContent(Integer contentId);

    /* 게시물 삭제 - 시설에 속해 있는지 또는 매니저인지 검증 */
    Integer validJoinFacility(String facilityJoinNum);

    /* 게시물 삭제 - 작성자 인지 검증 */
    Integer validContentWriter(String facilityJoinNum, Integer contentNum);

    /* 게시물 좋아요 */
    void facilityContentLike(RequestContentLike requestContentLike);

    /* 게시물 좋아요 취소*/
    void deleteContentLike(String userUuid, Integer contentNum);

    /* 게시물 좋아요 - 여부*/
    Integer contentLikeBool(String userUuid, Integer contentNum);

    /* 게시물 좋아요 개수 */
    Integer getContentLikeCount(Integer contentNum);

    /* 공지 불러오기 - ALL */
    List<FacilityNotice> getNoticeList(String facilityNun);

    /* 공지 불러오기 - Main Banner(5)*/
    List<FacilityNotice> getNoticeListMain(String facilityNun, Integer count);

    /* 게시물 리스트 불러오기 - ALL */
    List<FacilityContent> getContentList(String facilityNum);

    /* 게시물 리스트 불러오기 - Main Banner(5) */
    List<FacilityContent> getContentListMain(String facilityNum, Integer count);

    /* 게시물 총 개수 불러오기 */
    Integer getContentCount(String facilityNum);

    /* 내가 쓴 게시물 불러오기 - ALL */
    List<FacilityContent> getMyContent(String userUuid);

    /* 내가 쓴 게시물 불러오기 - Main */
    List<FacilityContent> getMyContentMain(String userUuid, Integer count);
}
