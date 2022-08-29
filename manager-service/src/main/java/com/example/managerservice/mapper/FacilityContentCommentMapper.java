package com.example.managerservice.mapper;

import com.example.managerservice.dto.FacilityContentCommentDto;
import com.example.managerservice.vo.ContentCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FacilityContentCommentMapper {

    /* 댓글 등록 */
    void registerComment(FacilityContentCommentDto fccd);

    /* 댓글 수정 */
    void updateComment(FacilityContentCommentDto fccd);

    /* 댓글 삭제 */
    void deleteComment(@Param("commentNum") Integer commentNum,
                       @Param("userUuid") String userUuid);

    /* 댓글 조회 */
    ContentCommentVo getComment(@Param("commentNum") Integer commentNum);

}
