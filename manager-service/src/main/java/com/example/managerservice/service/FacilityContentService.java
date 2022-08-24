package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import com.example.managerservice.mapper.FacilityContentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.managerservice.constant.FacilityContentConstant.*;


@Slf4j
@Service
public class FacilityContentService {
    private FacilityContentMapper fcm;

    @Autowired
    public FacilityContentService(FacilityContentMapper facilityContentMapper) {
        this.fcm = facilityContentMapper;
    }

    /* 게시물 등록 */
    public ResponseEntity registerContent(FacilityContentDto fcd){

        try{
            Integer valid = fcm.findValidFacilityContentNum(fcd.getFacilityContentNum());
            /* Content ID 중복 검사 */
            if(valid == 1){
                throw new Exception(FACILITY_CONTENT_CONFLICT);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(FACILITY_CONTENT_CONFLICT);
        }
        fcm.registerContent(fcd);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_REGISTER_SUCCESS);
    }

    /* 게시물 상세 조회 */
    /* 조건 : 해당 건물 등록 한 사람만 */
    public ResponseEntity contentDetailsView(String uuid,Integer contentId){
        try{
            fcm.facilityInUserValid(uuid, contentId);
            }catch (NullPointerException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_VIEW_VALID_FAIL);
            }
        return ResponseEntity.status(HttpStatus.OK).body(fcm.contentDetailView(contentId));
    }

    /* 게시물 삭제 */
    /* 조건 : 게시물 등록한사람, 게시물 번호 일치*/
    public String deleteContent(String uuid, String contentId){
        try{
            fcm.validContentOwner(uuid,contentId);
        }catch (NullPointerException e){
            return FACILITY_CONTENT_DELETE_VALID_FAIL;
        }
        fcm.deleteContent(uuid,contentId);
        return FACILITY_CONTENT_DELETE_VALID_COMPLETE;
    }

    /* 공지 리스트 불러오기 */
    public List<FacilityContentDto> getMyFacilityNoticeLt(String facilityNo){
        return fcm.getMyFacilityNoticeLt(facilityNo,  NOTICE_TAKE);
    }

    /* 게시물 리스트 불러오기 */
    public List<FacilityContentDto> getMyFacilityLt(String facilityNo){
        return fcm.getMyFacilityLt(facilityNo,  TAKE);
    }

    /* 게시물 좋아요 여부 확인 */
    public Integer ContentLikeBool(String uuid,Integer contentNum){
        return fcm.contentLikeBool(uuid,contentNum);
    }

    /* 게시물 좋아요 */
    public ResponseEntity facilityContentLike(FacilityContentLikeDto facilityContentLikeDto){
        fcm.facilityContentLike(facilityContentLikeDto);
        return ResponseEntity.status(HttpStatus.OK).body("좋아요");
    }

    /* 게시물 좋아요 취소*/
    public ResponseEntity deleteContentLike(String userUuid, Integer contentNum){
        fcm.deleteContentLike(userUuid,contentNum);
        return ResponseEntity.status(HttpStatus.OK).body("좋아요 취소");
    }

    /* 게시물 좋아요 개수 불러오기 */
    public Integer getLikeCount(Integer contentNum){
        return fcm.getLikeCount(contentNum);
    }


}
