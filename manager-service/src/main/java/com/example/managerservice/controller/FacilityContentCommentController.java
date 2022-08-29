package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityContentCommentDto;
import com.example.managerservice.service.FacilityContentCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class FacilityContentCommentController {

    private FacilityContentCommentService fccs;

    @Autowired
    public FacilityContentCommentController(FacilityContentCommentService fccs) {
        this.fccs = fccs;
    }

    /* 게시물 댓글 등록 */
    @PostMapping("/facility/content/comment")
    public ResponseEntity registerComment(@RequestBody FacilityContentCommentDto fccd){
        return ResponseEntity.status(HttpStatus.OK).body(fccs.registerComment(fccd));
    }

    /* 게시물 댓글 수정 */
    @PostMapping("/facility/content/comment/update")
    public ResponseEntity updateComment(@RequestBody FacilityContentCommentDto fccd){
        return ResponseEntity.status(HttpStatus.OK).body(fccs.updateComment(fccd));
    }

    /* 게시물 댓글 삭제 */
    @GetMapping("/facility/content/comment/delete/{commentNum}/{userUuid}")
    public ResponseEntity deleteComment(@PathVariable("commentNum") Integer commentNum,
                                        @PathVariable("userUuid") String userUuid){
        return ResponseEntity.status(HttpStatus.OK).body(fccs.deleteComment(commentNum, userUuid));
    }

    /* 게시물 조회*/
    @GetMapping("/facility/content/comment/get/{commentNum}")
    public ResponseEntity getComment(@PathVariable("commentNum") Integer commentNum){
        return ResponseEntity.status(HttpStatus.OK).body(fccs.getComment(commentNum));
    }
}
