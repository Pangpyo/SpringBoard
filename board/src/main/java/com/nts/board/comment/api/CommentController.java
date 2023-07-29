package com.nts.board.comment.api;

import com.nts.board.comment.application.CommentService;
import com.nts.board.comment.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postPk}/comment")
public class CommentController {

    private final CommentService commentService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @PostMapping
    public ResponseEntity<String> commentAdd(@PathVariable Long postPk, @RequestBody CommentRequestDto commentRequestDto) {
        if (commentService.addComment(postPk, commentRequestDto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

}
