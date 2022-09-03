package com.example.userservice.service;

import com.example.userservice.mapper.ImageMapper;
import com.example.userservice.vo.GetProfileImageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.userservice.constant.SignUpConstant.SAVE_PATH;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class ImageUploader {

    public ImageUploader(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }
    private ImageMapper imageMapper;

    public static String upload(MultipartFile multipartFile, String uuid) throws IOException {
        String savaPath = SAVE_PATH;
        File file = new File(  System.getProperty("user.dir") + "/" + uuid);
        multipartFile.transferTo(file);
        log.info("upload : 로컬서버 이미지 저장");

        return "이미지가 저장되었습니다.";
    }

    public String getImageFile(GetProfileImageVo getProfileImageVo){
        return imageMapper.getImageFile(getProfileImageVo);
    }
}
