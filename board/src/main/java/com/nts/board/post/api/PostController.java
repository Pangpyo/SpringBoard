package com.nts.board.post.api;

import com.nts.board.post.application.PostService;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @PostMapping
    public ResponseEntity<String> insertPost(@RequestBody PostSaveRequestDto PostRequstdto) {
        if (postService.savePost(PostRequstdto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{postPk}/checkPassword")
    public ResponseEntity<String> checkPassword(@PathVariable Long postPk, @RequestBody String requestPassword) {
        System.out.println(postPk + " : " + requestPassword);
        if (postService.comparePassword(postPk, requestPassword)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{postPk}")
    public ResponseEntity<String> updatePost(@PathVariable Long postPk, @RequestBody PostSaveRequestDto postRequstdto) {
        if (postService.modifyPost(postPk, postRequstdto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPostList(@RequestParam int page) {
        Page<PostListResponseDto> posts = postService.findPostList(page);
        if (posts != null && !posts.isEmpty()) {
            return new ResponseEntity<Page<PostListResponseDto>>(posts, HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
