package com.nts.board.comment.api;

import com.nts.board.comment.application.CommentService;
import com.nts.board.comment.dto.CommentRequestDto;
import com.nts.board.comment.dto.CommentResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postPk}/comment")
@Api( tags = "게시글 내부의 댓글에 대한 API" )
public class CommentController {

    private final CommentService commentService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @PostMapping
    @ApiOperation(value = "댓글 입력")
    public ResponseEntity<String> commentAdd(@PathVariable Long postPk, @RequestBody CommentRequestDto commentRequestDto) {
        if (commentService.addComment(postPk, commentRequestDto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @ApiOperation(value = "댓글 조회", notes = "해당 게시글의 모든 댓글 조회")
    public ResponseEntity<?> commentList(@PathVariable Long postPk, @RequestParam int page) {
        Page<CommentResponseDto> comments = commentService.findCommentList(postPk, page);
        if (comments != null && comments.getSize() != 0) {
            return new ResponseEntity<Page<CommentResponseDto>>(comments, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{commentPk}")
    @ApiOperation(value = "댓글 삭제", notes = "해당 게시글을 삭제상태로 바꿈")
    public ResponseEntity<?> commentRemove(@PathVariable String postPk, @PathVariable Long commentPk) {
        commentService.removeComment(commentPk);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

}
