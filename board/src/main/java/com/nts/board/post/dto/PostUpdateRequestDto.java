package com.nts.board.post.dto;

import com.nts.board.post.domain.Hashtag;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostUpdateRequestDto {

    private String title;
    private String postContent;
    private List<String> hashtags;
}
