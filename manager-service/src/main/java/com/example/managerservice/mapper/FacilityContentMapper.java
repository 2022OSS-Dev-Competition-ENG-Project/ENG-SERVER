package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityContentDto;
import com.example.managerservice.dto.FacilityContentLikeDto;
import com.example.managerservice.vo.ContentListVo;
import com.example.managerservice.vo.GetMyContentVo;
import com.example.managerservice.vo.MainContentListVo;
import com.example.managerservice.vo.getUserContentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FacilityContentMapper {

    /* 게시물 등록*/
    void registerContent(FacilityContentDto contentDto);

    /* 게시물 등록 - 게시물 중복 검사 */
    Integer validFacilityContentNum(Integer contentNum);

    /* 게시물 등록 - 컨텐츠 넘버 가져오기  */
    Integer getContentNum(@Param("contentText") String contentText,
                          @Param("contentDate") LocalDateTime contentDate);

    /* 게시물 등록 - 이미지 링크 업데이트 */
    void contentImageUpdate(String contentImg, Integer contentNum);

    /* 특정 게시물 삭제 */
    void deleteContent(@Param("userUuid") String userUuid,
                       @Param("contentNum") Integer contentNum);

    /* 게시물 삭제 - 특정 게시물에 달려 있는 모든 댓글 삭제 하기 */
    void deleteAllComment(@Param("contentNum") Integer contentNum);

    /* 게시물 삭제 - 특정 게시물에 달려 있는 모든 좋아요 삭제 하기 */
    void deleteAllLike(@Param("contentNum") Integer contentNum);

    /* 게시물 삭제 - 특정 게시물 작성자인지 검증 */
    Integer validContentOwner(String userUuid, Integer contentNum);

    /* 특정 게시물 상세 조회 */
    getUserContentVo contentDetailView(@Param("contentNum") Integer contentNum,
                                       @Param("userUuid") String userUuid,
                                       @Param("nameTable") String nameTable,
                                       @Param("uuidColum") String uuidColum,
                                       @Param("nameColum") String nameColum,
                                       @Param("imgColum") String imgColum);

    /* 특정 게시물 상세 조회 - content_type 조회하기*/
    int getContentType(@Param("contentNum") Integer contentNum);

    /* 게시물 좋아요 */
    void facilityContentLike(FacilityContentLikeDto contentLikeDto);

    /* 게시물 좋아요 - 개수 불러오기 */
    Integer getLikeCount(Integer contentNum);

    /* 게시물 좋아요 - 취소 */
    void deleteContentLike(String userUuid, Integer contentNum);

    /* 게시물 좋아요 - 여부*/
    Integer contentLikeBool(String userUuid, Integer contentNum);

    /* 게시물 리스트 불러오기 - 매안용 */
    List<MainContentListVo> getContentListMain(
            @PathVariable("facilityNo") String facilityNo,  /* 시설물 번호 */
            @PathVariable("type") Integer type,             /* 공지, 일반 게시물 종류 */
            @PathVariable("count") Integer count);          /* 불러올 개수 */

    /* 게시물 리스트 불러오기 */
    List<ContentListVo> getContentList(
            @Param("facilityNum") String facilityNum, /* 시설물 번호*/
            @Param("type") Integer type,            /* 공지, 일반 게시물 종류 */
            @Param("position") Integer position,    /* Page Index */
            @Param("take") Integer take,            /* 불러올 개수 */
            @Param("subTable") String subTable,     /* Join 시킬 테이블 */
            @Param("subType") String subType,       /* Join 시킬 조건 */
            @Param("subValue") String subValue      /* Join 시킬 컬럼 */
    );

    /* 게시물 리스트 불러오기 - 게시물 총 개수 불러오기 */
    Integer getContentCount(@Param("facilityNo") String facilityNo, /* 시설물 번호 */
                            @Param("type") Integer type);           /* 공지, 일반 게시물 종류 */

    Integer getContentValid(Integer contentId);


    /* 게시물 삭제 - 매니저용*/
    void deleteContentManager(@Param("contentNum") Integer contentNum,
                              @Param("facilityNo") String facilityNo);

    /* 내가 쓴 게시물 불러오기 5개만 */
    List<GetMyContentVo> getMyContentLt(String userUuid);

    /* 내가 쓴 게시물 불러오기 */
    List<GetMyContentVo> getMyContent(String userUuid);


}
