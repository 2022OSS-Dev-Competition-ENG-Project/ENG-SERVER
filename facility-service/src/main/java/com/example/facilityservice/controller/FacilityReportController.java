package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityReport;
import com.example.facilityservice.service.FacilityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
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
    @GetMapping("report/{facilityNum}")
    public ResponseEntity getReport(@PathVariable("facilityNum") Integer facilityNum){
        ResponseEntity responseEntity = facilityReportService.getReportList(facilityNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
