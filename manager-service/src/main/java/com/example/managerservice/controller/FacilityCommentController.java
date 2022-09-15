package com.example.managerservice.controller;

import com.example.managerservice.dto.FacilityCommentDto;
import com.example.managerservice.service.FacilityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FacilityCommentController {

    private FacilityCommentService fcs;

    @Autowired
    public FacilityCommentController(FacilityCommentService fcs) {
        this.fcs = fcs;
    }

    /* 게시물 댓글 등록 */
    @PostMapping("/facility/content/comment")
    public ResponseEntity registerComment(@RequestBody FacilityCommentDto fccd) {
        return ResponseEntity.status(HttpStatus.OK).body(fcs.registerComment(fccd));
    }

    /* 게시물 댓글 수정 */
    @PostMapping("/facility/content/comment/update")
    public ResponseEntity updateComment(@RequestBody FacilityCommentDto fccd) {
        return ResponseEntity.status(HttpStatus.OK).body(fcs.updateComment(fccd));
    }

    /* 게시물 댓글 삭제 */
    @GetMapping("/facility/content/comment/delete/{commentNum}/{userUuid}")
    public ResponseEntity deleteComment(@PathVariable("commentNum") Integer commentNum,
                                        @PathVariable("userUuid") String userUuid) {
        return ResponseEntity.status(HttpStatus.OK).body(fcs.deleteComment(commentNum, userUuid));
    }

    /* 게시물 댓글 조회 */
    @GetMapping("/facility/content/comment/{contentNum}")
    public ResponseEntity getComment(@PathVariable("contentNum") Integer contentNum) {
        return ResponseEntity.status(HttpStatus.OK).body(fcs.getComment(contentNum));
    }

}
