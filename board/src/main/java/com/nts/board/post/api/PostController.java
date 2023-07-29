package com.nts.board.post.api;

import com.nts.board.post.application.PostService;
import com.nts.board.post.dto.*;
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
    public ResponseEntity<String> postAdd(@RequestBody PostSaveRequestDto postSaveRequestdto) {
        if (postService.addPost(postSaveRequestdto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{postPk}/checkPassword")
    public ResponseEntity<String> passwordCheck(@PathVariable Long postPk, @RequestBody String requestPassword) {
        if (postService.checkPassword(postPk, requestPassword)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{postPk}")
    public ResponseEntity<String> postModify(@PathVariable Long postPk, @RequestBody PostUpdateRequestDto postUpdateRequestdto) {
        postService.modifyPost(postPk, postUpdateRequestdto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> postList(@RequestParam int page) {
        Page<PostListResponseDto> posts = postService.findPostList(page);
        if (posts != null && !posts.isEmpty()) {
            return new ResponseEntity<Page<PostListResponseDto>>(posts, HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{postPk}")
    public ResponseEntity<PostResponseDto> postDetail(@PathVariable Long postPk) {
        postService.updateHits(postPk);
        PostResponseDto post = postService.findPostDetail(postPk);
        return new ResponseEntity<PostResponseDto>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{postPk}")
    public ResponseEntity<String> postRemove(@PathVariable Long postPk) {
        postService.removePost(postPk);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping("/{postPk}")
    public ResponseEntity<String> postLike(@PathVariable Long postPk, @RequestParam boolean isLike) {
        postService.likePost(postPk, isLike);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> postSearch(@RequestParam SearchField searchField, @RequestParam String searchKeyword, @RequestParam int page) {
        Page<PostListResponseDto> postSearchResults = postService.searchPost(searchField, searchKeyword, page);
        if (postSearchResults != null && postSearchResults.getSize() != 0) {
            return new ResponseEntity<Page<PostListResponseDto>>(postSearchResults, HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
