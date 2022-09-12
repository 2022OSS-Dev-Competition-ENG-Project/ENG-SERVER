package com.example.userservice.service;

import com.example.userservice.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.userservice.constant.ImageConstant.SAVE_PATH;

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

    public static void upload(MultipartFile multipartFile, String userImg, String userUuid) throws IOException {
        String savaPath = SAVE_PATH;
        File file = new File(  savaPath + userUuid);
        multipartFile.transferTo(file);
        imageMapper.upload(multipartFile, userImg, userUuid);
    }
}