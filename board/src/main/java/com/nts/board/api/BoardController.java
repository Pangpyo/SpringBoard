package com.nts.board.api;

import com.nts.board.BoardCountDto;
import com.nts.board.comment.application.CommentService;
import com.nts.board.post.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/count")
    public ResponseEntity<BoardCountDto> boardCount() {
        Long postCount = postService.countPost();
        Long commentCount = commentService.countComment();
        BoardCountDto boardCountDto =
                BoardCountDto.builder()
                .postCount(postCount)
                .commentCount(commentCount)
                .build();
        return new ResponseEntity<BoardCountDto>(boardCountDto,HttpStatus.OK);
    }
}
