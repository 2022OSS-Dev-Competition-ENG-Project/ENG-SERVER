package com.example.facilityservice.mapper;

import com.example.facilityservice.dto.FacilityComment;
import com.example.facilityservice.vo.ResponseCommentList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityCommentMapper {

    /* 게시물 댓글 등록 */
    void registerComment(FacilityComment facilityComment);

    /* 댓글 작성자 검증 */
    Integer validCommentWriter(@Param("commentNum") Integer commentNum,
                               @Param("userUuid") String userUuid);

    /* 게시물 댓글 수정 */
    void updateComment(FacilityComment facilityComment);

    /* 게시물 댓글 삭제 */
    void deleteComment(@Param("commentNum") Integer commentNum,
                       @Param("userUuid") String userUuid);

    /* 게시물 댓글 조회 */
    List<ResponseCommentList> getComment(@Param("contentNum") Integer contentNum);
}
