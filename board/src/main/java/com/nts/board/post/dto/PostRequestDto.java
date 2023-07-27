package com.nts.board.post.dto;

import com.nts.board.post.domain.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class PostRequestDto {

    private String title;
    private String postContent;
    private String postAuthor;
    private String password;
    private List<String> hashtags;

    @Builder

    public PostRequestDto(String title, String postContent, String postAuthor, String password, List<String> hashtags) {
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.password = password;
        this.hashtags = hashtags;
    }
}
