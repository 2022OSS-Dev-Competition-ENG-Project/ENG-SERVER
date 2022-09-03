package com.example.userservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface ImageMapper {
    String upload(MultipartFile multipartFile, String userImg, String userUuid);
}
