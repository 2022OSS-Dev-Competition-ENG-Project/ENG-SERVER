package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityReport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityReportMapper {
    /* 신고 하기 */
    void registerReport(FacilityReport facilityReport);

    /* 신고 하기 - 이미지 업데이트 */
    void updateReportImage(int reportNum, String reportImg);
}
