package com.nts.board.post.application;

import com.nts.board.post.dao.HashtagRepository;
import com.nts.board.post.dao.PostRepository;
import com.nts.board.post.domain.Hashtag;
import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long savePost(PostSaveRequestDto postSaveRequestDto) {
        // 사용자가 설정한 해시태그들중 없는 것들은 해시태그 테이블에 추가한 후 리스트로 만든다
        List<Hashtag> hashtagList = postSaveRequestDto.getHashtags().stream()
                .map(hashtag -> hashtagRepository.existsByText(hashtag) ? hashtagRepository.findByText(hashtag) :
                        hashtagRepository.save(Hashtag.builder().text(hashtag).build()))
                .collect(Collectors.toList());
        return postRepository
                .save(Post.builder()
                        .title(postSaveRequestDto.getTitle())
                        .postContent(postSaveRequestDto.getPostContent())
                        .hashtags(hashtagList)
                        .build())
                        .getPostPk();
    }

    public boolean comparePassword(Long postPk, String requestPassword) {
        return postRepository.findById(postPk)
                .orElseThrow(() -> new EntityNotFoundException())
                .getPassword().equals(requestPassword);
    }
    @Transactional
    public Long modifyPost(Long postPk, PostSaveRequestDto postSaveRequestDto) {
        Post post = postRepository.findById(postPk)
                .orElseThrow(() -> new EntityNotFoundException());
        post.update(postSaveRequestDto.getTitle(), postSaveRequestDto.getPostContent());
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
                .build());
    }
}
