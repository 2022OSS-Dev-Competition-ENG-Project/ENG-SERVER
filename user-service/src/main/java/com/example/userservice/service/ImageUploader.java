package com.example.userservice.service;

import com.example.userservice.constant.ImageConstant;
import com.example.userservice.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.userservice.constant.ImageConstant.*;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class ImageUploader {
    @Autowired
    public ImageUploader(ImageMapper imageMapper){
        this.imageMapper = imageMapper;
    }
    private static ImageMapper imageMapper;

    /* User ProfileImage 저장 */
    public static ResponseEntity upload(MultipartFile multipartFile, String userUuid) throws IOException {
        String savaPath = SAVE_PATH;
        String userImg = "http://jlchj.iptime.org/user-service/ProfileImage/" + userUuid;
        File file = new File(  savaPath + userUuid);
        /* 저장될 폴더가 있는지 확인*/
        if (!new File(savaPath).exists()) {
            try {
                /* 저장될 폴더가 없다면 생성*/
                new File(savaPath).mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        multipartFile.transferTo(file);
        imageMapper.upload(multipartFile, userImg, userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ImageConstant.IMAGE_SUCCESS);
    }
}