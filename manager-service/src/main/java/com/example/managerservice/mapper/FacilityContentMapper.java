package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityContentDto;
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
}
