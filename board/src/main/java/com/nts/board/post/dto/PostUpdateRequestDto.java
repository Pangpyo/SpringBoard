package com.nts.board.post.dto;

import lombok.*;

import java.util.List;


@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostUpdateRequestDto {

    private String title;
    private String postContent;
    private List<String> hashtags;
}
