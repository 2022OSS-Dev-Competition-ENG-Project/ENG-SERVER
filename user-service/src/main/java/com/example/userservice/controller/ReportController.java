package com.example.userservice.controller;

import com.example.userservice.dto.ReportDto;
import com.example.userservice.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
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
        reportService.reportRegister(reportDto);
        /* 신고 하기 - reportNum 가져오기*/
        int rd = reportService.getReportNum(reportDto.getReportText(),reportDto.getReportDate());
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
}
