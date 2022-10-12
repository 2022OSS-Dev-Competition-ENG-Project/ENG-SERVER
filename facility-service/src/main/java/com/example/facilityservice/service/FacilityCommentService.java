package com.example.facilityservice.service;

import com.example.facilityservice.dto.FacilityComment;
import com.example.facilityservice.mapper.FacilityCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FacilityCommentService {

    private FacilityCommentMapper facilityCommentMapper;
    private LocalDateTime now = LocalDateTime.now();
    @Autowired
    public FacilityCommentService(FacilityCommentMapper facilityCommentMapper) {
        this.facilityCommentMapper = facilityCommentMapper;
    }

    /* 게시물 댓글 등록 */
    public ResponseEntity registerComment(FacilityComment facilityComment) {
        /* 현재 시간 가져와서 시간을 저장 */
        LocalDateTime datetime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                now.getHour(),
                now.getMinute(),
                now.getSecond());
        facilityComment.setCommentDate(datetime);
        facilityCommentMapper.registerComment(facilityComment);

        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 댓글을 등록하였습니다.");
    }

    /* 게시물 댓글 수정 */
    public ResponseEntity updateComment(FacilityComment facilityComment) {
        /* 댓글 작성자 검증 */
        if(facilityCommentMapper.validCommentWriter(facilityComment.getCommentNum(),facilityComment.getUserUuid()) == 1){
            /* 게시물 댓글 수정 */
            facilityCommentMapper.updateComment(facilityComment);
            return ResponseEntity.status(HttpStatus.OK).body("정상적으로 댓글 수정되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글을 작성한 작성자만 댓글을 수 할수 있습니다.");
        }
    }

    /* 게시물 댓글 삭제 */
    public ResponseEntity deleteComment(Integer commentNum, String userUuid) {
        if (facilityCommentMapper.validCommentWriter(commentNum,userUuid) == 1){
            facilityCommentMapper.deleteComment(commentNum,userUuid);
            return ResponseEntity.status(HttpStatus.OK).body("정상적으로 댓글 삭제되었습니다.");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body("댓글을 작성한 작성자만 댓글을 삭제 할수 있습니다.");
        }
    }

    /* 게시물 댓글 조회 */
    public ResponseEntity getComment(Integer contentNum) {
        return ResponseEntity.status(HttpStatus.OK).body(facilityCommentMapper.getComment(contentNum));
    }
}
