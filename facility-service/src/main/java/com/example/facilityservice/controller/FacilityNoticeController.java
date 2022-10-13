package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.service.FacilityNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class FacilityNoticeController {

    private FacilityNoticeService facilityNoticeService;

    @Autowired
    public FacilityNoticeController(FacilityNoticeService facilityNoticeService) {
        this.facilityNoticeService = facilityNoticeService;
    }

    /* 공지 생성 */
    @PostMapping(value = "/notice/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity registerContent(@RequestPart FacilityNotice facilityNotice,
                                          @RequestPart(required = false) MultipartFile image) throws Exception {
        ResponseEntity responseEntity = facilityNoticeService.createNotice(facilityNotice, image);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지 상세 보기 */
    @GetMapping("/notice/view/{noticeNum}")
    public ResponseEntity viewNoticeDetail(@PathVariable("noticeNum") Integer noticeNum) {
        ResponseEntity responseEntity = facilityNoticeService.viewNoticeDetail(noticeNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 공지 불러오기 - Main Banner(5) */
    @GetMapping("/notice/{facilityNum}/main")
    public ResponseEntity getNoticeListMain( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityNoticeService.getNoticeList(facilityNum, 5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 공지 불러오기 - ALL */
    @GetMapping("/notice/{facilityNum}")
    public ResponseEntity getNoticeList( @PathVariable("facilityNum") String facilityNum) {
        ResponseEntity responseEntity = facilityNoticeService.getNoticeList(facilityNum, null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
