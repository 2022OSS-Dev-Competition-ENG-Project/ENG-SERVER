package com.example.managerservice.service;

import com.example.managerservice.dto.FacilityContentCommentDto;
import com.example.managerservice.mapper.FacilityContentCommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.managerservice.constant.FacilityContentConstant.*;

@Slf4j
@Service
public class FacilityContentCommentService {

    private FacilityContentCommentMapper fccm;
    private LocalDate now = LocalDate.now();

    @Autowired
    public FacilityContentCommentService(FacilityContentCommentMapper fccm) {
        this.fccm = fccm;
    }


    /* 댓글 등록 */
    public ResponseEntity registerComment(FacilityContentCommentDto fccd){
        fccd.setContentCommentDate(now);
        fccm.registerComment(fccd);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_REGISTER_COMPLETE);
    }

    /* 댓글 수정 */
    public ResponseEntity updateComment(FacilityContentCommentDto fccd){
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
        log.info(commentNum.toString(), commentText);
        return ResponseEntity.status(HttpStatus.OK).body(COMMENT_REGISTER_UPDATE);
    }


    /* 댓글 조회 */
    public ResponseEntity getComment(Integer commentNum){

        /* 사용자가 이 게시물이 속해있는 시설물 소속인가 */
        return ResponseEntity.status(HttpStatus.OK).body(fccm.getComment(commentNum));
    }


}
