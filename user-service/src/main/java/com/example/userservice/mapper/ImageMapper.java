package com.example.userservice.mapper;

import com.example.userservice.vo.GetProfileImageVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    String getImageFile(GetProfileImageVo getProfileImageVo);
}
