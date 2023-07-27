package com.nts.board.post.application;

import com.nts.board.post.dao.HashtagRepository;
import com.nts.board.post.dao.PostRepository;
import com.nts.board.post.domain.Hashtag;
import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;

    public Long savePost(PostRequestDto dto) {
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String hashtag : dto.getHashtags()) {
            if (!hashtagRepository.existsByText(hashtag)) {
                hashtagList.add(hashtagRepository.save(Hashtag.builder().text(hashtag).build()));
            }else {
                hashtagList.add(hashtagRepository.findByText(hashtag));
            }
        }
        return postRepository
                .save(Post.builder()
                        .title(dto.getTitle())
                        .postContent(dto.getPostContent())
                        .postAuthor(dto.getPostAuthor())
                        .password(dto.getPassword())
                        .hashtags(hashtagList)
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
