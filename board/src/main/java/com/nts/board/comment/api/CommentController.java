package com.nts.board.comment.api;

import com.nts.board.comment.application.CommentService;
import com.nts.board.comment.dto.CommentRequestDto;
import com.nts.board.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> commentList(@PathVariable Long postPk, @RequestParam int page) {
        Page<CommentResponseDto> comments = commentService.findCommentList(postPk, page);
        if (comments != null && comments.getSize() != 0) {
            return new ResponseEntity<Page<CommentResponseDto>>(comments, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
        }
    }

}
