package com.example.managerservice.controller;


import com.example.managerservice.dto.ReportDto;
import com.example.managerservice.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ReportController {

    private ReportService reportService;
    private LocalDateTime now = LocalDateTime.now();


    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "/report/register")
    public ResponseEntity reportRegister(@RequestPart ReportDto reportDto,
                                         @RequestPart(required = false) List<MultipartFile> files) throws Exception{
        LocalDateTime datetime = LocalDateTime.of(now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());

        /* 신고 시간 저장 */
        reportDto.setReportDate(datetime);
        /* 신고 저장*/
            int rp = reportService.reportRegister(reportDto);
            log.info("contentNum : " + rp);

        /* 신고 하기 - reportNum 가져오기*/
        int rd = rp;
        /* 이미지가 있다면 이미지 생성 reportDto img Update*/
        if (!files.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (String item : reportService.saveContentImage(files,reportDto.getFacilityNo(),rd)){
                sb.append(item + " ");
            }
            /* reportDto에 reportImg를 방금 들어온 경로로 업데이트 한다. */
            reportDto.setReportImg(sb.toString());
            reportService.reportImgUpdate(rd,reportDto.getReportImg());
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    /* 신고 상세 보기 */
    @GetMapping("/report/{reportNum}")
    public ResponseEntity getReport(@PathVariable("reportNum") Integer reportNum){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getReport(reportNum));
    }

    /* 내가 신고한 리스트 불러오기 5개만 */
    @GetMapping("/report/list/main/{userUuid}")
    public ResponseEntity getMyReportLt(@PathVariable("userUuid") String userUuid){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getMyReportLt(userUuid));
    }

    /* 내가 신고한 리스트 불러오기 */
    @GetMapping("/report/list/{userUuid}")
    public ResponseEntity getMyReport(@PathVariable("userUuid") String userUuid){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getMyReport(userUuid));
    }

    /* 신고 처리 현황 별 리스트 */
    @GetMapping("/report/list/{facilityNo}/{status}")
    public ResponseEntity getFacilityReport(@PathVariable("facilityNo") String facilityNo,
                                            @PathVariable("status") Integer status){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getFacilityReport(facilityNo,status));
    }

    /* 신고 처리 하기 */
    @GetMapping("/report/{reportNum}/{status}")
    public ResponseEntity updateReport(@PathVariable("reportNum") Integer reportNum,
                                       @PathVariable("status") Integer status){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.updateReport(reportNum,status));
    }
}
