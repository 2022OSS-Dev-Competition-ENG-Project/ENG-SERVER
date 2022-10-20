package com.example.facilityservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QrMapper {

    /* Qr 불러오기 */
    String findQr(@Param("facilityNum") String facilityNum);
}
