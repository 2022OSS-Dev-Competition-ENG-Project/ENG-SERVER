package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityContent;
import com.example.facilityservice.service.FacilityContentService;
import com.example.facilityservice.vo.RequestContentLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class FacilityContentController {

    private FacilityContentService facilityContentService;

    @Autowired
    public FacilityContentController(FacilityContentService facilityContentService) {
        this.facilityContentService = facilityContentService;
    }

    /* 게시물 생성 */
    @PostMapping("/content/create")
    public ResponseEntity createContent(@RequestPart FacilityContent facilityContent) {
        ResponseEntity responseEntity = facilityContentService.createContent(facilityContent);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 상세 보기 */
    @GetMapping("/content/{userUuid}/{contentNum}")
    public ResponseEntity viewContentDetail(@PathVariable("userUuid") String userUuid,
                                            @PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityContentService.viewContentDetail(userUuid, contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 삭제 - Manager */
    @GetMapping("/content/delete/mg/{contentNum}/{managerUuid}")
    public ResponseEntity deleteContentManager(@PathVariable("contentNum") Integer contentNum,
                                               @PathVariable("managerUuid") String managerUuid) {
        ResponseEntity responseEntity = facilityContentService.deleteContentManager(contentNum, managerUuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 삭제 - User */
    @GetMapping("/content/delete/{contentNum}/{userUuid}")
    public ResponseEntity deleteContent(@PathVariable("userUuid") String userUuid,
                                        @PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityContentService.deleteContent(userUuid, contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 좋아요 */
    @PostMapping("/content/liked")
    public ResponseEntity contentLike(@RequestBody RequestContentLike requestContentLike) {
        ResponseEntity responseEntity = facilityContentService.contentLike(requestContentLike);
        return  ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 좋아요 개수 불러오기 */
    @GetMapping("/content/liked/{contentNum}")
    public ResponseEntity getLikeCount(@PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityContentService.getContentLikeCount(contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 리스트 불러오기 - Main Banner(5) */
    @GetMapping("/content/{facilityNum}/main")
    public ResponseEntity getContentListMain( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentList(facilityNum, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 리스트 불러오기 - ALL */
    @GetMapping("/content/{facilityNum}")
    public ResponseEntity getContentList( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentList(facilityNum,null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 총 개수 불러오기 */
    @GetMapping("/content/{facilityNum}/count")
    public ResponseEntity getContentCount(@PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityContentService.getContentCount(facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 쓴 게시물 불러오기 - Main */
    @GetMapping("/my/content/main/{userUuid}")
    public ResponseEntity getMyContentMain(@PathVariable("userUuid") String userUuid) {
        ResponseEntity responseEntity = facilityContentService.getMyContent(userUuid, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 내가 쓴 게시물 불러오기 - ALL */
    @GetMapping("/my/content/{userUuid}")
    public ResponseEntity getMyContent(@PathVariable("userUuid") String userUuid) {
        ResponseEntity responseEntity = facilityContentService.getMyContent(userUuid, null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


}
