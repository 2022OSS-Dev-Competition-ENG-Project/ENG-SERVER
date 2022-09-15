package com.example.managerservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QrMapper {

    /* QR 불러오기 */
    String getQRCode(@Param("facilityName") String facilityName,
                     @Param("facilityAddress") String facilityAddress);
}
