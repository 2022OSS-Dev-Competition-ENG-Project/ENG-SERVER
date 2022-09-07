package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityCommentDto;
import com.example.managerservice.vo.ContentCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityCommentMapper {

    /* 댓글 등록 */
    void registerComment(FacilityCommentDto fccd);

    /* 댓글 수정 */
    void updateComment(FacilityCommentDto fccd);

    /* 댓글 삭제 */
    void deleteComment(@Param("commentNum") Integer commentNum,
                       @Param("userUuid") String userUuid);

    /* 댓글 조회 */
    List<ContentCommentVo> getComment(@Param("contentNum") Integer contentNum);
}
