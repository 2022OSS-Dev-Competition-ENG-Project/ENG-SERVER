package com.example.managerservice.mapper;

import com.example.managerservice.vo.GetQRUrlVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QrMapper {

    String getQRCode(GetQRUrlVo getQRUrlVo);

}
