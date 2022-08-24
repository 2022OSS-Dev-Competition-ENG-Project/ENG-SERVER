package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityContentMapper {

    /* 게시물 등록*/
    void registerContent(FacilityContentDto facilityContentDto);

    /* 게시물 중복 검사 */
    Integer findValidFacilityContentNum(Integer facilityContentNum);

    /* 게시물 작성자 검증 */
    void validContentOwner(String userUuid, String facilityNo);

    /* 게시물 삭제*/
    void deleteContent(String userUuid,String facilityContentNum);

    /* 특정 게시물 상세 조회 */
    FacilityContentDto contentDetailView(Integer facilityContentNum);

    /* 사용자 게시물 보기전 시설물 소속 조회 */
    FacilityContentDto facilityInUserValid(String userUuid, Integer facilityContentNum);

    /* 공지 게시물 전부 불러오기*/
    List<FacilityContentDto> getMyFacilityNoticeLt(String facilityNo, Integer count);

    /* 일반 게시물 전부 불러오기*/
    List<FacilityContentDto> getMyFacilityLt(String facilityNo, Integer count);

    /* 게시물 좋아요 */
    void facilityContentLike(FacilityContentLikeDto facilityContentLikeDto);

    /* 게시물 좋아요 개수 불러오기 */
    Integer getLikeCount(Integer contentNum);

    /* 게시물 좋아요 취소 */
    void deleteContentLike(String userUuid, Integer contentNum);

    /* 게시물 좋아요 여부*/
    Integer contentLikeBool(String userUuid, Integer contentNum);
}
