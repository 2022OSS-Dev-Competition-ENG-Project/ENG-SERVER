package com.example.managerservice.service;


import com.example.managerservice.dto.ReportDto;
import com.example.managerservice.mapper.ReportMapper;
import com.example.managerservice.vo.GetMyReportList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.managerservice.constant.ReportConstant.REPORT_IMAGES_SAVE_DB_PATH;
import static com.example.managerservice.constant.ReportConstant.REPORT_IMAGES_SAVE_PATH;


@Slf4j
@Service
public class ReportService {

    private ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    /* 신고 하기 */
    public Integer reportRegister(ReportDto reportDto){
        reportMapper.reportRegister(reportDto);
        return reportDto.getReportNum();
    }

    /* 신고 하기 - 신고 이미지 로컬 저장 & URL 만들  */
    /* Content Image 저장 */
    public List<String> saveContentImage(List<MultipartFile> files, String facilityNo, Integer reportNum) throws Exception {


        String savePath = REPORT_IMAGES_SAVE_PATH;

        String createFolderPath = savePath + facilityNo + '/' + reportNum +'/';
        List newlist = new ArrayList<>();
        /* 저장될 폴더가 있는지 확인*/
        if(!new File(createFolderPath).exists()){
            try {
                /* 저장될 폴더가 없다면 생성*/
                new File(createFolderPath).mkdirs();
            }catch (Exception e){
                e.getStackTrace();
            }
        }

        for (MultipartFile file : files){
            /* 파일 명 변수에 저장*/
            String originalFileName = file.getOriginalFilename();
            /* File Object 생성 */
            String filePath =savePath + facilityNo + '/' + reportNum + '/' + originalFileName;
            File object = new File(filePath);
            /* list에 DB에 저장시킬 Location 추가*/
            newlist.add(REPORT_IMAGES_SAVE_DB_PATH + facilityNo + '&' + reportNum + '&' + originalFileName );
            /* 파일 전송 */
            file.transferTo(object);
        }
        return newlist;
    }

    /* 신고 하기 - 이미지 경로 업데이트 */
    public void reportImgUpdate(Integer reportNum,String reportImg){
        reportMapper.reportImgUpdate(reportNum ,reportImg);
    }

    public ReportDto getReport(Integer reportNum) {
        return reportMapper.getReport(reportNum);
    }

    /* 내가 신고한 리스트 불러오기 5개만 */
    public List<GetMyReportList> getMyReportLt(String userUuid) {
        return reportMapper.getMyReportLt(userUuid);
    }

    /* 내가 신고한 리스트 불러오기 */
    public List<GetMyReportList> getMyReport(String userUuid) {
        return reportMapper.getMyReportLt(userUuid);
    }

    /* 신고 처리 현황 별 리스트 */
    public List<GetMyReportList> getFacilityReport(String facilityNo, Integer status) {
        String searchValue = null;

        switch (status) {
            case 0:
                searchValue = "미처리";
                break;
            case 1:
                searchValue = "처리";
                break;
            case 2:
                searchValue = "반려";
                break;
        }
        return reportMapper.getFacilityReport(facilityNo,searchValue);
    }


    /* 신고 처리*/
    public String updateReport(Integer reportNum, Integer status) {

        String searchValue = null;

        switch (status) {
            case 0:
                searchValue = "미처리";
                break;
            case 1:
                searchValue = "처리";
                break;
            case 2:
                searchValue = "반려";
                break;
        }

        reportMapper.updateReport(reportNum, searchValue);
        return searchValue + "되었습니다.";
    }

    /* 신고 리스트 불러오기 - 매니저 메인페이지에서 5개 */
    public List<GetMyReportList> getReportFacility(String facilityNo) {
        return reportMapper.getReportFacility(facilityNo);
    }
}
