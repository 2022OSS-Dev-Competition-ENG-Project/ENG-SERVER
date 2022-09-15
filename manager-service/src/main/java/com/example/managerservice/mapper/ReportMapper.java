package com.example.managerservice.mapper;


import com.example.managerservice.dto.ReportDto;
import com.example.managerservice.vo.GetMyReportList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReportMapper {

    /* 신고하기 */
    void reportRegister(ReportDto reportDto);

    /* 신고 하기 - 이미지 경로 업데이트 */
    void reportImgUpdate(@Param("reportNum") Integer reportNum
            , @Param("reportImg") String reportImg);

    /* 신고 상세 보기 */
    ReportDto getReport(Integer reportNum);

    /* 신고 처리 하기 */
    void updateReport(@Param("reportNum") Integer reportNum,
                      @Param("status") String status);

    /* 신고 처리 현황 별 리스트 */
    List<GetMyReportList> getFacilityReport(String facilityNo, String searchValue);

    /* 내가 신고한 리스트 5개만 */
    List<GetMyReportList> getMyReportLt(@Param("userUuid") String userUuid);

    /* 신고 리스트 불러오기 - 매니저 메인페이지에서 5개 */
    List<GetMyReportList> getReportFacilityLt(@Param("facilityNo") String facilityNo);
}
