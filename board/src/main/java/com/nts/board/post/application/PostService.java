package com.nts.board.post.application;

import com.nts.board.post.dao.PostRepository;
import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long savePost(PostRequestDto dto) {
        return postRepository
                .save(Post.builder()
                        .title(dto.getTitle())
                        .postContent(dto.getPostContent())
                        .postAuthor(dto.getPostAuthor())
                        .password(dto.getPassword())
                        .build())
                        .getPostPk();
    }

    public boolean modifyPost(Long postPk, PostRequestDto dto) {
        Post post = postRepository.findById(postPk)
                .orElse(null);
        if (post != null && dto.getPostAuthor().equals(post.getPostAuthor()) &&
        dto.getPassword().equals(post.getPassword())) {
            post.update(dto.getTitle(), dto.getPostContent());
            postRepository.save(post);
            return true;
        }
        return false;
    }


    public List<PostListResponseDto> findPostList() {
        return postRepository.findAllByOrderByPostPkDesc();
    }
}
