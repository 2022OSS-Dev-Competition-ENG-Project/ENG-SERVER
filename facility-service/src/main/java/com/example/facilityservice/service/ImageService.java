package com.example.facilityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.facilityservice.constant.FacilityContentConstant.FACILITY_CONTENT_IMAGE_SAVE_PATH;
import static com.example.facilityservice.constant.FacilityContentConstant.FACILITY_CONTENT_IMAGE_SAVE_PATH_DB;

@Service
public class ImageService {

    /* Content Image 저장 */
    public String saveContentImage(MultipartFile file, Integer facilityNo, Integer contentNum) throws Exception {

        String savePath = FACILITY_CONTENT_IMAGE_SAVE_PATH;
        String createFolderPath = savePath + facilityNo + '/' + contentNum.toString() + '/';

        /* 저장될 폴더가 있는지 확인*/
        if (!new File(createFolderPath).exists()) {
            try {
                /* 저장될 폴더가 없다면 생성*/
                new File(createFolderPath).mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        /* 파일 명 변수에 저장*/
        String originalFileName = file.getOriginalFilename();

        /* File Object 생성 */
        String filePath = savePath + facilityNo + '/' + contentNum + '/' + originalFileName;
        File object = new File(filePath);

        /* list에 DB에 저장시킬 Location 추가*/
        String saveDbPath = FACILITY_CONTENT_IMAGE_SAVE_PATH_DB + facilityNo + '&' + contentNum + '&' + originalFileName;

        /* 파일 전송 */
        file.transferTo(object);

        return saveDbPath;
    }

}
