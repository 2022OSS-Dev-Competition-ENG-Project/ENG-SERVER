package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.service.FacilityContentService;
import com.example.facilityservice.vo.RequestContentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class FacilityContentController {

    private FacilityContentService facilityContentService;

    @Autowired
    public FacilityContentController(FacilityContentService facilityContentService) {
        this.facilityContentService = facilityContentService;
    }


    /* 게시물 등록 */
    @PostMapping(value = "/content/create")
    public ResponseEntity createContent(@RequestPart FacilityContent facilityContent) {
        ResponseEntity responseEntity = facilityContentService.createContent(facilityContent);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 상세 보기 */
    @GetMapping("/content/{facilityJoinNum}/{contentId}")
    public ResponseEntity viewContentDetail(@PathVariable("facilityJoinNum") Integer facilityJoinNum,
                                            @PathVariable("contentId") Integer contentId) {
        ResponseEntity responseEntity = facilityContentService.viewContentDetail(facilityJoinNum, contentId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지 등록 */
    @PostMapping(value = "/facility/notice/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity registerContent(@RequestPart FacilityContent facilityContent,
                                          @RequestPart(required = false) MultipartFile image) throws Exception {
        ResponseEntity responseEntity = facilityContentService.createNotice(facilityContent, image);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지 상세 보기 */
    @GetMapping("/content/{facilityJoinNum}/{noticeId}")
    public ResponseEntity viewNoticeDetail(@PathVariable("facilityJoinNum") Integer facilityJoinNum,
                                           @PathVariable("noticeId") Integer noticeId) {
        ResponseEntity responseEntity = facilityContentService.viewNoticeDetail(facilityJoinNum, noticeId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 삭제 - Manager */
    @GetMapping("/facility/content/delete/mg/{facilityJoinNum}/{contentId}")
    public ResponseEntity deleteContentManager(@PathVariable("contentId") Integer contentId,
                                               @PathVariable("facilityJoinNum") String facilityJoinNum) {
        ResponseEntity responseEntity = facilityContentService.deleteContentManager(contentId, facilityJoinNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    /* 게시물 삭제 - User */
    @GetMapping("/facility/content/delete/{facilityJoinNum}/{contentNum}")
    public ResponseEntity deleteContent(@PathVariable("facilityJoinNum") String facilityJoinNum,
                                        @PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityContentService.deleteContent(facilityJoinNum, contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 좋아요 */
    @PostMapping("/facility/content/liked")
    public ResponseEntity contentLike(@RequestBody RequestContentLike requestContentLike) {
        ResponseEntity responseEntity = facilityContentService.contentLike(requestContentLike);
        return  ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 좋아요 개수 불러오기 */
    @GetMapping("/facility/content/liked/{contentNum}")
    public ResponseEntity getLikeCount(@PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityContentService.getContentLikeCount(contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지 불러오기 - Main Banner(5) */
    @GetMapping("/facility/notice/{facilityNum}/main")
    public ResponseEntity getNoticeListMain( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getNoticeList(facilityNum, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 공지 불러오기 - ALL */
    @GetMapping("/facility/notice/{facilityNum}")
    public ResponseEntity getNoticeList( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getNoticeList(facilityNum, null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 리스트 불러오기 - Main Banner(5) */
    @GetMapping("/facility/content/{facilityNum}/main")
    public ResponseEntity getContentListMain( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentList(facilityNum, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 리스트 불러오기 - ALL */
    @GetMapping("/facility/content/{facilityNum}")
    public ResponseEntity getContentList( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentList(facilityNum,null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 총 개수 불러오기 */
    @GetMapping("/facility/content/getcount/{facilityNum}/")
    public ResponseEntity getContentCount(@PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentCount(facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 쓴 게시물 불러오기 - Main */
    @GetMapping("/facility/content/main/user/{userUuid}")
    public ResponseEntity getMyContentMain(@PathVariable("userUuid") String userUuid) {
        ResponseEntity responseEntity = facilityContentService.getMyContent(userUuid, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 내가 쓴 게시물 불러오기 - ALL */
    @GetMapping("/facility/my/content/{userUuid}")
    public ResponseEntity getMyContent(@PathVariable("userUuid") String userUuid) {
        ResponseEntity responseEntity = facilityContentService.getMyContent(userUuid, null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


}
