package com.example.userservice.mapper;

import com.example.userservice.dto.ReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface ReportMapper {

        /* 신고하기 */
        void reportRegister(ReportDto reportDto);
        /* 신고 하기 - reportNum 가져오기*/
        Integer getReportNum(@Param("reportText") String reportText,
                         @Param("reportDate") LocalDateTime reportDate);

        /* 신고 하기 - 이미지 경로 업데이트 */
        void reportImgUpdate(@Param("reportNum") Integer reportNum
                ,@Param("reportImg") String reportImg);
}
