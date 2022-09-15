package com.example.managerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.managerservice.constant.FacilityContentConstant.FACILITY_CONTENT_IMAGE_SAVE_PATH;
import static com.example.managerservice.constant.FacilityContentConstant.FACILITY_CONTENT_IMAGE_SAVE_PATH_DB;

@Slf4j
@Service
public class ImageService {

    /* Content Image 저장 */
    public List<String> saveContentImage(List<MultipartFile> files, String facilityNo, Integer contentNum) throws Exception {

        String savePath = FACILITY_CONTENT_IMAGE_SAVE_PATH;
        String createFolderPath = savePath + facilityNo + '/' + contentNum.toString() + '/';
        List newlist = new ArrayList<>();
        /* 저장될 폴더가 있는지 확인*/
        if (!new File(createFolderPath).exists()) {
            try {
                /* 저장될 폴더가 없다면 생성*/
                new File(createFolderPath).mkdirs();
                log.info("폴더를 생성합니다.");
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        for (MultipartFile file : files) {
            /* 파일 명 변수에 저장*/
            String originalFileName = file.getOriginalFilename();
            /* File Object 생성 */
            String filePath = savePath + facilityNo + '/' + contentNum + '/' + originalFileName;
            File object = new File(filePath);
            /* list에 DB에 저장시킬 Location 추가*/
            newlist.add(FACILITY_CONTENT_IMAGE_SAVE_PATH_DB + facilityNo + '&' + contentNum + '&' + originalFileName);
            /* 파일 전송 */
            file.transferTo(object);
        }
        return newlist;
    }

//    public ResponseEntity<Resource> getImage(String path) throws IOException {

//
//        log.info(savePath);
//        Resource resource = new FileSystemResource(savePath);
//
//        if (!resource.exists())
//            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
//        HttpHeaders headers = new HttpHeaders();
//        Path filePath = null;
//
//        try {
//            filePath = Paths.get(savePath);
//            headers.add("Content-Type", Files.probeContentType(filePath));
////            headers.add("Content-Type", Files.probeContentType(filePath));
//        } catch (IOException e){
//            e.getStackTrace();
//        }
//        return new ResponseEntity<>(resource,headers,HttpStatus.OK);
//    }


}
