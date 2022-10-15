package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityReport;
import com.example.facilityservice.service.FacilityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("")
public class FacilityReportController {

    private FacilityReportService facilityReportService;

    @Autowired
    public FacilityReportController(FacilityReportService facilityReportService) {
        this.facilityReportService = facilityReportService;
    }

    /* 신고 하기 */
    @PostMapping(value = "/report/register")
    public ResponseEntity registerReport(@RequestPart FacilityReport facilityReport,
                                         @RequestPart(required = false) List<MultipartFile> images) throws Exception {
        ResponseEntity responseEntity = facilityReportService.registerReport(facilityReport,images);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }

    /* 신고 목록 불러오기 */
    @GetMapping("/report/list/{facilityNum}/{status}")
    public ResponseEntity getReportList(@PathVariable("facilityNum") Integer facilityNum,
                                    @PathVariable("status") Integer status){
        ResponseEntity responseEntity = facilityReportService.getReportList(facilityNum, status);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 신고 상세 보기 */
    @GetMapping("/report/{reportNum}")
    public ResponseEntity getReport(@PathVariable("reportNum") Integer reportNum){
        ResponseEntity responseEntity = facilityReportService.getReport(reportNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 내가 신고한 리스트 불러오기 - Main */
    @GetMapping("/report/my/main/{userUuid}")
    public ResponseEntity getMyReportListMain (@PathVariable("userUuid") String userUuid){
        ResponseEntity responseEntity = facilityReportService.getMyReportList(userUuid,5);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 내가 신고한 리스트 불러오기 - All*/
    @GetMapping("/report/my/{userUuid}")
    public ResponseEntity getMyReportList (@PathVariable("userUuid") String userUuid){
        ResponseEntity responseEntity = facilityReportService.getMyReportList(userUuid, null);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
