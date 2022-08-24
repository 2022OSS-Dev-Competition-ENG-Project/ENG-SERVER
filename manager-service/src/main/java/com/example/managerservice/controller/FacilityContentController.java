package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import com.example.managerservice.service.FacilityContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class FacilityContentController {

    private FacilityContentService fcs;

    @Autowired
    public FacilityContentController(FacilityContentService fcs) {
        this.fcs = fcs;
    }

    /* 게시물 등록 */
    @PostMapping("/facility/content/register")
    public ResponseEntity registerContent(@RequestBody FacilityContentDto facilityContentDto){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.registerContent(facilityContentDto));
    }

    /*게시물 상세 보가*/
    @GetMapping("/facility/content/{uuid}/{contentId}")
    public ResponseEntity contentDetailView(@PathVariable("uuid") String uuid,
                                            @PathVariable("contentId") Integer contentId){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.contentDetailsView(uuid,contentId));
    }

    /* 게시물 좋아요 */
    /* 예외 처리 필요 */
    @PostMapping("/facility/content/liked")
    public ResponseEntity contentLike(@RequestBody FacilityContentLikeDto facilityContentLikeDto) {
        /* 좋아요 여부 알아보기 */
        if(fcs.ContentLikeBool(
                facilityContentLikeDto.getUserUuid(),
                facilityContentLikeDto.getContentNum()
        ) == 0) /* 좋아요 누른 상태가 아닐 때*/
        {
            return ResponseEntity.status(HttpStatus.OK).body(fcs.facilityContentLike(facilityContentLikeDto));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteContentLike(
                    facilityContentLikeDto.getUserUuid(),
                    facilityContentLikeDto.getContentNum()));
        }
    }

    /* 게시물 좋아요 개수 불러오기 */
    /* 예외 처리 필요 */
    @GetMapping("/facility/content/liked/{contentNum}")
    public ResponseEntity getLikeCount(@PathVariable("contentNum")Integer contentNum){

        log.info(contentNum.toString());
        try {
        fcs.getLikeCount(contentNum).toString();
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
//
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getLikeCount(contentNum));
    }

    /*  게시물 삭제 */
    @GetMapping("/facility/content/delete/{uuid}/{contentId}")
    public ResponseEntity deleteContent(@PathVariable("uuid") String uuid,
                                        @PathVariable("contentId") String contentId){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteContent(uuid, contentId));
    }

    /* 메인 배너에 보일 공지 게시물 불러오기 */
    @GetMapping("/facility/{facilityNo}/content/notice/main")
    public ResponseEntity getMyFacilityNoticeLt(@PathVariable("facilityNo") String facilityNo){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getMyFacilityNoticeLt(facilityNo));
    }

    /* 메인 배너에 보일 일반 게시물 불러오기 */
    @GetMapping("/facility/{facilityNo}/content/main")
    public ResponseEntity getMyFacilityLt(@PathVariable("facilityNo") String facilityNo){
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getMyFacilityLt(facilityNo));
    }

}
