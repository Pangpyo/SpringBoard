package com.nts.board.post.dto;

import com.nts.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString
public class PostListResponseDto {
    private Long postPk;
    private String title;
    private Date createdAt;
    private String postAuthor;
    private int hit;
    private int postLike;
    private int commentCount;
    private boolean isNew;

    @Builder
    public PostListResponseDto(Long postPk, String title, Date createdAt, String postAuthor, int hit, int postLike, int commentCount, boolean isNew) {
        this.postPk = postPk;
        this.title = title;
        this.createdAt = createdAt;
        this.postAuthor = postAuthor;
        this.hit = hit;
        this.postLike = postLike;
        this.commentCount = commentCount;
        this.isNew = isNew;
    }
}
