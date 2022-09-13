package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import com.example.managerservice.mapper.FacilityContentMapper;
import com.example.managerservice.vo.ContentListVo;
import com.example.managerservice.vo.GetMyContentVo;
import com.example.managerservice.vo.MainContentListVo;
import com.example.managerservice.vo.getUserContentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.managerservice.constant.FacilityContentConstant.*;


@Slf4j
@Service
public class FacilityContentService {
    private FacilityContentMapper fcm;
    LocalDateTime localDateTime = LocalDateTime.now();

    @Autowired
    public FacilityContentService(FacilityContentMapper facilityContentMapper) {
        this.fcm = facilityContentMapper;
    }

    /* 게시물 등록 */
    public long registerContent(FacilityContentDto fcd){
        if (fcd.getContentType() == 1){

        }
        fcm.registerContent(fcd);
        long cn = fcd.getContentNum();
        return cn;
    }

    /* 게시물 상세 조회 - */
    public getUserContentVo contentDetailsView(Integer contentId, String userUuid){
        int type = fcm.getContentType(contentId);
        String nameTable, uuidColum, nameColum, imgColum;

        if(type == 1){
            nameTable = "manager";
            uuidColum = "manager_uuid";
            nameColum = "manager_nickname";
            imgColum = "manager_image";
        }else {
            nameTable = "user";
            uuidColum = "user_uuid";
            nameColum = "user_nickname";
            imgColum = "user_img";
        }

        return fcm.contentDetailView(contentId,userUuid, nameTable, uuidColum, nameColum, imgColum);
    }

    /* 게시물 등록하기 - 이미지 링크 업데이트*/
    public void contentImageUpdate(String contentImg, int contentNum) {
        fcm.contentImageUpdate(contentImg, contentNum);
    }

    /* 게시물 삭제 */
    public ResponseEntity deleteContent(String uuid, Integer contentId){

        if (fcm.validContentOwner(uuid,contentId) == 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_DELETE_VALID_FAIL);
        }else{
            fcm.deleteAllLike(contentId);
            fcm.deleteAllComment(contentId);
            fcm.deleteContent(uuid,contentId);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_DELETE_VALID_COMPLETE);
        }
    }

    /* 게시물 삭제 - 매니저용 */
    public ResponseEntity deleteContentManager(Integer contentId, String facility_no) {
        fcm.deleteAllLike(contentId);
        fcm.deleteAllComment(contentId);
        fcm.deleteContentManager(contentId,facility_no);
        return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_DELETE_VALID_COMPLETE);
    }

    /* 게시물 좋아요 여부 확인 */
    public Integer contentLikeBool(String uuid,Integer contentNum){
        return fcm.contentLikeBool(uuid,contentNum);
    }

    /* 게시물 좋아요 */
    public ResponseEntity facilityContentLike(FacilityContentLikeDto facilityContentLikeDto){
        fcm.facilityContentLike(facilityContentLikeDto);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_LIKE);
    }

    /* 게시물 좋아요 - 취소*/
    public ResponseEntity deleteContentLike(String userUuid, Integer contentNum){
        fcm.deleteContentLike(userUuid,contentNum);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_LIKE_CANCEL);
    }

    /* 게시물 좋아요 - 개수 불러오기 */
    public Integer getLikeCount(Integer contentNum){
        return fcm.getLikeCount(contentNum);
    }

    /* 게시물 리스트 불러오기 - 매안용 */
    public List<MainContentListVo> getContentListMain(String facilityNo, Integer type){
        return fcm.getContentListMain(facilityNo, type, TAKE);
    }


    /* 게시물 리스트 불러오기 - 전체용 */
    public List<ContentListVo> getContentList(String facilityNo,
                                              Integer type,
                                              Integer position) {
        position = position * CONTENT_LIST_CUL;

        String subTable;
        String subType;
        String subValue;

        if(type == 1){
            subTable = CONTENT_LIST_MANAGER_TABLE;
            subType = CONTENT_LIST_MANAGER_TYPE;
            subValue = CONTENT_LIST_MANAGER_VALUE;
        }else {
            subTable = CONTENT_LIST_USER_TABLE;
            subType = CONTENT_LIST_USER_TYPE;
            subValue = CONTENT_LIST_USER_VALUE;
        }

        log.info(
                "facilityNum : " + facilityNo +
                ", type : " + type +
                ", position : " + position +
                ", subTable : " + subTable +
                ", subType : " + subType +
                ", subValue : " + subValue
        );
        return fcm.getContentList(facilityNo, type, position, CONTENT_LIST_CUL, subTable, subType, subValue);
    }

    /* 게시물 총 개수 불러오기 */
    public Integer getContentCount(String facilityNo, Integer type){
        return fcm.getContentCount(facilityNo,type);
    }

    public Integer getContentNum(String contentText, LocalDateTime dateTime) {
        return fcm.getContentNum(contentText, dateTime);
    }


    /* 내가 쓴 게시물 불러오기 5개만 */
    public List<GetMyContentVo> getMyContentLt(String userUuid) {
        return fcm.getMyContentLt(userUuid);
    }

    /* 내가 쓴 게시물 불러오기 */
    public List<GetMyContentVo> getMyContent(String userUuid) {
        return fcm.getMyContent(userUuid);
    }
}
