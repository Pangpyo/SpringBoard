package com.nts.board.post.application;

import com.nts.board.post.dao.HashtagRepository;
import com.nts.board.post.dao.PostRepository;
import com.nts.board.post.domain.Hashtag;
import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;


    public Long addPost(PostSaveRequestDto postSaveRequestDto) {
        if (postSaveRequestDto.getHashtags().size() > 5) {
            throw new IllegalArgumentException();
        }
        // 사용자가 설정한 해시태그들중 없는 것들은 해시태그 테이블에 추가한 후 리스트로 만든다
        Set<Hashtag> hashtags = postSaveRequestDto.getHashtags().stream()
                .map(hashtag -> hashtagRepository.existsByText(hashtag) ? hashtagRepository.findByText(hashtag) :
                        hashtagRepository.save(Hashtag.builder().text(hashtag).build()))
                .collect(Collectors.toSet());
        return postRepository
                .save(Post.builder()
                        .title(postSaveRequestDto.getTitle())
                        .postContent(postSaveRequestDto.getPostContent())
                        .postAuthor(postSaveRequestDto.getPostAuthor())
                        .hashtags(hashtags)
                        .password(postSaveRequestDto.getPassword())
                        .build())
                .getPostPk();
    }

    public boolean checkPassword(Long postPk, String requestPassword) {
        return postRepository.findById(postPk)
                .orElseThrow(() -> new EntityNotFoundException())
                .getPassword().equals(requestPassword);
    }

    public Long modifyPost(Long postPk, PostUpdateRequestDto postUpdateRequestDto) {
        if (postUpdateRequestDto.getHashtags().size() > 5) {
            throw new IllegalArgumentException();
        }
        Post post = postRepository.findById(postPk)
                .orElseThrow(() -> new EntityNotFoundException());
        Set<Hashtag> hashtags = postUpdateRequestDto.getHashtags().stream()
                .map(hashtag -> hashtagRepository.existsByText(hashtag) ? hashtagRepository.findByText(hashtag) :
                        hashtagRepository.save(Hashtag.builder().text(hashtag).build()))
                .collect(Collectors.toSet());
        post.update(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getPostContent(), hashtags);
        postRepository.save(post);
        return post.getPostPk();
    }


    public Page<PostListResponseDto> findPostList(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("postPk").descending());
        Page<Post> posts = postRepository.findAllBy(pageRequest);
        return posts.map(post -> PostListResponseDto.builder()
                .postPk(post.getPostPk())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .postAuthor(post.getPostAuthor())
                .hit(post.getHit())
                .postLike(post.getPostLike())
                .commentCount(post.getComments().size())
                .isNew(isPostNew(post.getCreatedAt()))
                .build());
    }

    public PostResponseDto findPostDetail(Long postPk) {
        Post post = postRepository.findById(postPk)
                .orElseThrow(() -> new EntityNotFoundException());
        return PostResponseDto.builder()
                .postPk(post.getPostPk())
                .title(post.getTitle())
                .postContent(post.getPostContent())
                .postAuthor(post.getPostAuthor())
                .hit(post.getHit())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .postLike(post.getPostLike())
                .hashtags(post.getHashtags())
                .build();
    }

    public void removePost(Long postPk) {
        postRepository.deleteById(postPk);
    }

    @Transactional
    public void updateHits(Long postPk) {
        postRepository.updateHits(postPk);
    }

    @Transactional
    public void likePost(Long postPk, boolean isLike) {
        int likeValue = isLike ? 1 : -1;
        postRepository.updateLikes(postPk, likeValue);
    }

    public Long countPost() {
        return postRepository.count();
    }

    public Page<PostListResponseDto> searchPost(SearchField searchField, String searchKeyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("postPk").descending());
        Specification<Post> searchSpec = null;
        if (searchField == searchField.hashtag) {
            searchSpec =
                    ((root, query, criteriaBuilder) -> {
                        Join<Post, Hashtag> hashtagJoin = root.join("hashtags");
                        return criteriaBuilder.equal(hashtagJoin.get("text"), searchKeyword);
                    });
        } else {
            searchSpec =
                    ((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get(searchField.toString()), "%" + searchKeyword + "%"));
        }
        Page<Post> postSearchResults = postRepository.findAll(searchSpec, pageRequest);
        return postSearchResults.map(post -> PostListResponseDto.builder()
                .postPk(post.getPostPk())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .postAuthor(post.getPostAuthor())
                .hit(post.getHit())
                .postLike(post.getPostLike())
                .commentCount(post.getComments().size())
                .isNew(isPostNew(post.getCreatedAt()))
                .build());
    }


    private boolean isPostNew(Date createdAt) {
        Long withInThreeDays = new Date().getTime() - 1000 * 60 * 60 * 24 * 3L;
        return createdAt.getTime() >= withInThreeDays;
    }
}
