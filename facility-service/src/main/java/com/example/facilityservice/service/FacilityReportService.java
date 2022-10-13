package com.example.facilityservice.service;

import com.example.facilityservice.dto.FacilityReport;
import com.example.facilityservice.mapper.FacilityReportMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.facilityservice.constant.FacilityReportConstant.REPORT_REGISTER_COMPLETE;

@Service
public class FacilityReportService {
    private ReportImageService reportImageService;
    private FacilityReportMapper facilityReportMapper;
    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public FacilityReportService(ReportImageService reportImageService, FacilityReportMapper facilityReportMapper) {
        this.reportImageService = reportImageService;
        this.facilityReportMapper = facilityReportMapper;
    }


    /* 신고 하기 */
    public ResponseEntity registerReport(FacilityReport facilityReport, List<MultipartFile> files) throws Exception {
        LocalDateTime datetime = LocalDateTime.of(now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());

        /* 신고 시간 저장 */
        facilityReport.setReportDate(datetime);

        /* 신고 저장*/
        facilityReportMapper.registerReport(facilityReport);

        /* 이미지가 있다면 이미지 생성 reportDto img Update*/
        if (files != null) {
            StringBuilder sb = new StringBuilder();
            for (String item : reportImageService.saveContentImage(files, facilityReport.getFacilityNum(), facilityReport.getReportNum())) {
                sb.append(item + " ");
            }
            /* reportDto에 reportImg를 방금 들어온 경로로 업데이트 한다. */
            facilityReport.setReportImg(sb.toString());
            facilityReportMapper.updateReportImage(facilityReport.getReportNum(), facilityReport.getReportImg());
        }
        return ResponseEntity.status(HttpStatus.OK).body(REPORT_REGISTER_COMPLETE);
    }

    /* 신고 목록 불러오기 */
    public ResponseEntity getReportList(Integer facilityNum,Integer status) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityReportMapper.getReportList(facilityNum, status));
    }

    /* 신고 상세 보기 */
    public ResponseEntity getReport(@Param("reportNum") Integer reportNum){
        return ResponseEntity.status(HttpStatus.OK).body(facilityReportMapper.getReport(reportNum));
    }

    /* 내가 신고한 리스트 불러오기 */
    public ResponseEntity getMyReportList(String facilityNum, String userUuid,Integer count) {
        /* 5개 limit */
        if(count == null){
            return ResponseEntity.status(HttpStatus.OK).body(facilityReportMapper.getMyReport(facilityNum,userUuid));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(facilityReportMapper.getMyReportMain(facilityNum, userUuid,count));
        }
    }
}
