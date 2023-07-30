package com.nts.board.post.api;

import com.nts.board.post.application.PostService;
import com.nts.board.post.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Api( tags = "게시글 CRUD, 좋아요, 검색 API" )
public class PostController {
    private final PostService postService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @PostMapping
    @ApiOperation(value = "게시글 저장", notes = "관련 해시태그도 함께 입력")
    public ResponseEntity<String> postAdd(@RequestBody PostSaveRequestDto postSaveRequestdto) {
        if (postService.addPost(postSaveRequestdto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{postPk}/checkPassword")
    @ApiOperation(value = "게시글 패스워드 확인", notes = "삭제, 수정을 위해 암호화된 게시글 암호 비교")
    public ResponseEntity<String> passwordCheck(@PathVariable Long postPk, @RequestBody String requestPassword) {
        if (postService.checkPassword(postPk, requestPassword)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(FAIL, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{postPk}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 해시태그와 함께 수정")
    public ResponseEntity<String> postModify(@PathVariable Long postPk, @RequestBody PostUpdateRequestDto postUpdateRequestdto) {
        postService.modifyPost(postPk, postUpdateRequestdto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "게시글 리스트 조회", notes = "최신순 10개씩 조회")
    public ResponseEntity<?> postList(@RequestParam int page) {
        Page<PostListResponseDto> posts = postService.findPostList(page);
        if (posts != null && !posts.isEmpty()) {
            return new ResponseEntity<Page<PostListResponseDto>>(posts, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{postPk}")
    @ApiOperation(value = "게시글 디테일 조회", notes = "해시태그와 함께 조회, 조회수 상승")
    public ResponseEntity<PostResponseDto> postDetail(@PathVariable Long postPk) {
        postService.updateHits(postPk);
        PostResponseDto post = postService.findPostDetail(postPk);
        return new ResponseEntity<PostResponseDto>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{postPk}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseEntity<String> postRemove(@PathVariable Long postPk) {
        postService.removePost(postPk);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PatchMapping("/{postPk}")
    @ApiOperation(value = "게시글 좋아요", notes = "isLike에 따라 좋아요 업다운 가능")
    public ResponseEntity<String> postLike(@PathVariable Long postPk, @RequestParam boolean isLike) {
        postService.likePost(postPk, isLike);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "게시글 검색", notes = "searchField에 따라 검색 조건, searchKeyword에 따라 검색어 설정")
    public ResponseEntity<?> postSearch(@RequestParam SearchField searchField, @RequestParam String searchKeyword, @RequestParam int page) {
        Page<PostListResponseDto> postSearchResults = postService.searchPost(searchField, searchKeyword, page);
        if (postSearchResults != null && postSearchResults.getSize() != 0) {
            return new ResponseEntity<Page<PostListResponseDto>>(postSearchResults, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
