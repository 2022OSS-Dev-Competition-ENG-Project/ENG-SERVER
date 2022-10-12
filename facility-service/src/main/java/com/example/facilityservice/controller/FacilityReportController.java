package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityReport;
import com.example.facilityservice.service.FacilityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
                                         @RequestPart(required = false) List<MultipartFile> files) throws Exception {
        ResponseEntity responseEntity = facilityReportService.registerReport(facilityReport,files);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }
}
