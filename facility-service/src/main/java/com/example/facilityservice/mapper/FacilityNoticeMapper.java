package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityNotice;
import com.example.facilityservice.vo.ResponseGetNoticeList;
import com.example.facilityservice.vo.ResponseNoticeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityNoticeMapper {

    /* 공지 생성 */
    Integer createNotice(FacilityNotice facilityNotice);

    /* 공지 생성 - 이미지 업데이트 */
    void contentImageUpdate(@Param("saveDbPath") String saveDbPath,
                            @Param("noticeNum") Integer noticeNum);

    /* 공지 상세 보기 */
    ResponseNoticeDetail viewNoticeDetail(@Param("noticeNum") Integer noticeNum);


    /* 공지 리스트 불러오기 - All */
    List<ResponseGetNoticeList> getNoticeList(@Param("facilityNum") String facilityNum);

    /* 공지 리스트 불러오기 - Main Banner(5) */
    List<ResponseGetNoticeList> getNoticeListLt(@Param("facilityNum") String facilityNum,
                                                @Param("count") Integer count);
}
