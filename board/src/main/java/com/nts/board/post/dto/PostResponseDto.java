package com.nts.board.post.dto;

import com.nts.board.post.domain.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Getter
@NoArgsConstructor
@ToString
public class PostResponseDto {
    private Long postPk;
    private String title;
    private String postContent;
    private String postAuthor;
    private int hit;
    private Date createdAt;
    private Date modifiedAt;
    private int postLike;
    private Set<Hashtag> hashtags = new HashSet<>();

    @Builder
    public PostResponseDto(Long postPk, String title, String postContent, String postAuthor, int hit, Date createdAt, Date modifiedAt, int postLike, Set<Hashtag> hashtags) {
        this.postPk = postPk;
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.hit = hit;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postLike = postLike;
        this.hashtags = hashtags;
    }
}
