package com.example.userservice.service;

import com.example.userservice.dto.ReportDto;
import com.example.userservice.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.userservice.constant.ReportConstant.REPORT_IMAGES_SAVE_DB_PATH;
import static com.example.userservice.constant.ReportConstant.REPORT_IMAGES_SAVE_PATH;

@Service
public class ReportService {

    private ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    /* 신고 하기 */
    public void reportRegister(ReportDto reportDto){
        reportMapper.reportRegister(reportDto);
    }
    /* 신고 하기 - reportNum 가져오기*/
    public Integer getReportNum(String reportText, LocalDateTime reportTime) {
        return reportMapper.getReportNum(reportText, reportTime);
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
}
