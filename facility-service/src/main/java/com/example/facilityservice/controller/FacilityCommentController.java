package com.example.facilityservice.controller;

import com.example.facilityservice.dto.FacilityComment;
import com.example.facilityservice.service.FacilityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class FacilityCommentController {

    private FacilityCommentService facilityCommentService;

    @Autowired
    public FacilityCommentController(FacilityCommentService facilityCommentService) {
        this.facilityCommentService = facilityCommentService;
    }

    /* 게시물 댓글 등록 */
    @PostMapping("/comment/register")
    public ResponseEntity registerComment(@RequestBody FacilityComment facilityComment) {
        ResponseEntity responseEntity = facilityCommentService.registerComment(facilityComment);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    /* 게시물 댓글 수정 */
    @PostMapping("/comment/update")
    public ResponseEntity updateComment(@RequestBody FacilityComment facilityComment) {
        ResponseEntity responseEntity = facilityCommentService.updateComment(facilityComment);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 게시물 댓글 삭제 */
    @GetMapping("/comment/delete/{commentNum}/{userUuid}")
    public ResponseEntity deleteComment(@PathVariable("commentNum") Integer commentNum,
                                        @PathVariable("userUuid") String userUuid) {
        ResponseEntity responseEntity = facilityCommentService.deleteComment(commentNum, userUuid);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    /* 게시물 댓글 조회 */
    @GetMapping("/comment/{contentNum}")
    public ResponseEntity getComment(@PathVariable("contentNum") Integer contentNum) {
        ResponseEntity responseEntity = facilityCommentService.getComment(contentNum);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
