package com.nts.board.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PostRequestDto {

    private String title;
    private String postContent;
    private String postAuthor;
    private String password;

    @Builder
    public PostRequestDto(String title, String postContent, String postAuthor, String password) {
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.password = password;
    }
}
