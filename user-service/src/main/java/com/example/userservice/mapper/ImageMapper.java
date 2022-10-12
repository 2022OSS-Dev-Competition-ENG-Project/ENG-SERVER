package com.example.userservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface ImageMapper {
    /* User ProfileImage 저장 */
    void upload(MultipartFile multipartFile, String userImg, String userId);
}