package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FacilityReportMapper {
    /* 신고 하기 */
    void registerReport(FacilityReport facilityReport);

    /* 신고 하기 - 이미지 업데이트 */
    void updateReportImage(@Param("reportNum") int reportNum,
                           @Param("reportImage") String reportImage);

    /* 신고 상세 보기 */

}
