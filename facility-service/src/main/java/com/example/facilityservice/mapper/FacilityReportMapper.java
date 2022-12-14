package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityReport;
import com.example.facilityservice.vo.ResponseReportList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityReportMapper {

    /* 신고 하기 */
    void registerReport(FacilityReport facilityReport);

    /* 신고 하기 - 이미지 업데이트 */
    void updateReportImage(@Param("reportNum") Integer reportNum,
                           @Param("reportImage") String reportImage);

    /* 신고 목록 불러오기 - Main All */
    List<ResponseReportList> getReportList(@Param("facilityNum") String facilityNum,
                                           @Param("status") Integer status);

    /* 신고 목록 불러오기 - Main*/
    List<ResponseReportList> getReportListMain(@Param("facilityNum") String facilityNum);

    /* 신고 상세 보기 */
    FacilityReport getReport(@Param("reportNum") Integer reportNum);

    /* 내가 신고한 리스트 불러오기 - All */
    List<ResponseReportList> getMyReport(@Param("userUuid") String userUuid);

    /* 내가 신고한 리스트 불러오기 - Main */
    List<ResponseReportList> getMyReportMain(@Param("userUuid") String userUuid,
                                             @Param("count") Integer count);

    /* 신고 처 */
    void changeReportStatus(@Param("reportNum") Integer reportNum,
                            @Param("type") Integer type);
}
