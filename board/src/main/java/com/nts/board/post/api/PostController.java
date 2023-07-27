package com.nts.board.post.api;

import com.nts.board.post.application.PostService;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @PostMapping
    public ResponseEntity<String> insertPost(@RequestBody PostRequestDto dto) {
        if (postService.savePost(dto) != 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{postPk}")
    public ResponseEntity<String> updatePost(@PathVariable Long postPk, @RequestBody PostRequestDto dto) {
        if (postService.modifyPost(postPk, dto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPostList() {
        List<PostListResponseDto> posts = postService.findPostList();
        if (posts != null && !posts.isEmpty()) {
            return new ResponseEntity<List<PostListResponseDto>>(posts, HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
