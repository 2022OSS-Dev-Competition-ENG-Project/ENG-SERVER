package com.example.facilityservice.service;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.mapper.FacilityContentMapper;
import com.example.facilityservice.vo.RequestContentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.facilityservice.constant.FacilityContentConstant.*;

@Service
public class FacilityContentService {

    private FacilityContentMapper facilityContentMapper;
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public FacilityContentService(FacilityContentMapper facilityContentMapper) {
        this.facilityContentMapper = facilityContentMapper;
    }

    /* 게시물 생성*/
    public ResponseEntity createContent(FacilityContent facilityContent) {

        /* 현재 시간 가져와서 시간을 저장 */
        LocalDateTime datetime = LocalDateTime.of(now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
        facilityContent.setContentDate(datetime);

        /* 게시물 생성*/
        facilityContentMapper.createContent(facilityContent);
        return ResponseEntity.status(HttpStatus.CREATED).body(FACILITY_CONTENT_CREATE);
    }

    /* 게시물 상세 보기 */
    public ResponseEntity viewContentDetail(String userUuid, Integer contentNum) {
        /* 게시물 상세 보기 - 존재 여부 */
        if (facilityContentMapper.contentBool(contentNum) == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FACILITY_VIEW_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityContentMapper.viewContentDetail(userUuid, contentNum));
    }



    /* 게시물 삭제 - Manager */
    public ResponseEntity deleteContentManager(Integer contentNum, String managerUuid) {
        /* 게시물 삭제 - 시설에 속해 있는지 또는 매니저인지 검증*/
        if(facilityContentMapper.validJoinFacility(managerUuid) == 1){
            facilityContentMapper.deleteContent(contentNum);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_MANAGER_DELETE_COMPLETE);
        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_MANAGER_VALID_FAIL);
        }
    }

    /* 게시물 삭제 - User */
    public ResponseEntity deleteContent(String userUuid, Integer contentNum) {
        /* 게시물을 작성한 본인인지 검증*/
        if(facilityContentMapper.validContentWriter(userUuid,contentNum) == 1){
            facilityContentMapper.deleteContent(contentNum);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_DELETE_VALID_COMPLETE);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_DELETE_VALID_FAIL);
        }
    }

    /* 게시물 좋아요 */
    public ResponseEntity contentLike(RequestContentLike requestContentLike) {
        /* 좋아요 여부 알아보기 */
        if (facilityContentMapper.contentLikeBool(
                requestContentLike.getUserUuid(),
                requestContentLike.getContentNum()) == 0) /* 좋아요 누른 상태가 아닐 때*/ {
            facilityContentMapper.facilityContentLike(requestContentLike.getUserUuid(), requestContentLike.getContentNum());
            return ResponseEntity.status(HttpStatus.OK).body(COMMENT_LIKE);
        } else {
            facilityContentMapper.deleteContentLike(requestContentLike.getUserUuid(), requestContentLike.getContentNum());
            return ResponseEntity.status(HttpStatus.OK).body(COMMENT_LIKE_CANCEL);
        }
    }

    /* 게시물 좋아요 개수 불러오기 */
    public ResponseEntity getContentLikeCount(Integer contentNum) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityContentMapper.getContentLikeCount(contentNum));
    }


    /* 게시물 불러오기 */
    public ResponseEntity getContentList(String facilityNum, Integer count) {
        List<FacilityContent> facilityContents;
        if(count == null){
            /* 게시물 리스트 불러오기 - ALL */
            facilityContents = facilityContentMapper.getContentList(facilityNum);
        }else {
            /* 게시물 리스트 불러오기 - Main Banner(5) */
            facilityContents = facilityContentMapper.getContentListLt(facilityNum, count);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityContents);
    }

    /* 게시물 총 개수 불러오기 */
    public ResponseEntity getContentCount(String facilityNum) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityContentMapper.getContentCount(facilityNum));
    }

    /* 내가 쓴 게시물 불러오기 */
    public ResponseEntity getMyContent(String userUuid, Integer count) {
        List<FacilityContent> facilityContents;
        if (count == null){
            /* 내가 쓴 게시물 불러오기 - ALL */
            facilityContents = facilityContentMapper.getMyContent(userUuid);
        }else {
            /* 내가 쓴 게시물 불러오기 - Main */
            facilityContents = facilityContentMapper.getMyContentMain(userUuid,count);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityContents);
    }
}
