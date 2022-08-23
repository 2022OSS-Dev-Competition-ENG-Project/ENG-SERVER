package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.service.FacilityContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
