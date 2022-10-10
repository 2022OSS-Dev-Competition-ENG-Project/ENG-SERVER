package com.example.facilityservice.service;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.mapper.FacilityContentMapper;
import com.example.facilityservice.vo.RequestContentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.facilityservice.constant.FacilityContentConstant.*;

@Service
public class FacilityContentService {

    private FacilityContentMapper facilityContentMapper;
    private ImageService imageService;
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public FacilityContentService(FacilityContentMapper facilityContentMapper, ImageService imageService) {
        this.facilityContentMapper = facilityContentMapper;
        this.imageService = imageService;
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

    /* 게시물 상세 보기*/
    public ResponseEntity viewContentDetail(Integer facilityJoinNum, Integer contentId) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityContentMapper.viewContentDetail(facilityJoinNum, contentId));
    }

    /* 공지 생성*/
    public ResponseEntity createNotice(FacilityContent facilityContent, MultipartFile image) throws Exception {

        /* 현재 시간 가져와서 시간을 저장 */
        LocalDateTime datetime = LocalDateTime.of(now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
        facilityContent.setContentDate(datetime);


        /* 2. 등록된 게시물의 번호와 시설물 번호를 들고 온다. */
        long contentNum = facilityContentMapper.createNotice(facilityContent);
        Integer facilityJoinNum = facilityContent.getFacilityJoinNum();

        /* 3. 게시물 번호와 시설물 번호를 들고 오면 ImageService.saveContentImage 로 보낸다. */
        String saveDbPath = imageService.saveContentImage(image, facilityJoinNum, (int) contentNum);
        facilityContentMapper.contentImageUpdate(saveDbPath, (int) contentNum);

        return ResponseEntity.status(HttpStatus.CREATED).body(FACILITY_NOTICE_CREATE);
    }
    /* 공지 상세 보기 */
    public ResponseEntity viewNoticeDetail(Integer facilityJoinNum, Integer noticeId) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityContentMapper.viewNoticeDetail(facilityJoinNum, noticeId));
    }

    /* 게시물 삭제 - Manager */
    public ResponseEntity deleteContentManager(Integer contentId, String facilityJoinNum) {
        /* 게시물 삭제 - 시설에 속해 있는지 또는 매니저인지 검증*/
        if(facilityContentMapper.validJoinFacility(facilityJoinNum) == 1){
            facilityContentMapper.deleteContent(contentId);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_MANAGER_DELETE_COMPLETE);
        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_MANAGER_VALID_FAIL);
        }
    }

    /* 게시물 삭제 - User */
    public ResponseEntity deleteContent(String facilityJoinNum, Integer contentNum) {
        /* 게시물을 작성한 본인인지 검증*/
        if(facilityContentMapper.validContentWriter(facilityJoinNum,contentNum) == 1){
            facilityContentMapper.deleteContent(contentNum);
            return ResponseEntity.status(HttpStatus.OK).body(FACILITY_CONTENT_DELETE_VALID_COMPLETE);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FACILITY_CONTENT_DELETE_VALID_FAIL);
        }
    }

    /* 게시물 좋아*/
    public ResponseEntity contentLike(RequestContentLike requestContentLike) {
        /* 좋아요 여부 알아보기 */
        if (facilityContentMapper.contentLikeBool(
                requestContentLike.getUserUuid(),
                requestContentLike.getContentNum()) == 0) /* 좋아요 누른 상태가 아닐 때*/ {
            facilityContentMapper.facilityContentLike(requestContentLike);
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


    /* 공지 불러오기 */
    public ResponseEntity getNoticeList(String facilityNum, Integer count) {
        List<FacilityNotice> facilityNotices;
        if(count == null){
            /* 공지 불러오기 - ALL */
            facilityNotices = facilityContentMapper.getNoticeList(facilityNum);
        }else {
            /* 공지 불러오기 - Main Banner(5)*/
            facilityNotices = facilityContentMapper.getNoticeListMain(facilityNum, count);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facilityNotices);
    }

    /* 게시물 불러오기 */
    public ResponseEntity getContentList(String facilityNum, Integer count) {
        List<FacilityContent> facilityContents;
        if(count == null){
            /* 게시물 리스트 불러오기 - ALL */
            facilityContents = facilityContentMapper.getContentList(facilityNum);
        }else {
            /* 게시물 리스트 불러오기 - Main Banner(5) */
            facilityContents = facilityContentMapper.getContentListMain(facilityNum, count);
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
