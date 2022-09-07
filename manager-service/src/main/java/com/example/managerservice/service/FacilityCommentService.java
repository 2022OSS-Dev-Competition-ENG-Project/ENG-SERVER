package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityCommentDto;
import com.example.managerservice.mapper.FacilityCommentMapper;
import com.example.managerservice.vo.ContentCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.managerservice.constant.FacilityContentConstant.*;

@Service
public class FacilityCommentService {

    private FacilityCommentMapper fccm;
    LocalDateTime now = LocalDateTime.now();

    @Autowired
    public FacilityCommentService(FacilityCommentMapper fccm) {
        this.fccm = fccm;
    }


    /* 댓글 등록 */
    public ResponseEntity registerComment(FacilityCommentDto fccd){
        fccd.setCommentDate(now);
        fccm.registerComment(fccd);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_REGISTER_COMPLETE);
    }

    /* 댓글 수정 */
    public ResponseEntity updateComment(FacilityCommentDto fccd){
        try{
            fccm.updateComment(fccd);
        }catch (NullPointerException e){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(COMMENT_REGISTER_UPDATE_FAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_REGISTER_UPDATE);
    }

    /* 댓글 삭제 */
    public ResponseEntity deleteComment(Integer commentNum, String commentText){
        fccm.deleteComment(commentNum, commentText);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_DELETE_COMPLETE);
    }


    /* 댓글 조회 */
    public List<ContentCommentVo> getComment(Integer contentNum){
        /* 유저인지 사용자인지 확인 하기 위함 */
        /* 사용자가 이 게시물이 속해있는 시설물 소속인가 */
        return fccm.getComment(contentNum);
    }

}
