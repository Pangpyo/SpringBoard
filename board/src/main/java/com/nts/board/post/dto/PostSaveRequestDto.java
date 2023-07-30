package com.nts.board.post.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String postContent;
    private String postAuthor;
    private String password;
    private List<String> hashtags;
}
