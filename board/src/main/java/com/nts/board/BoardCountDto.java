package com.nts.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardCountDto {
    private Long postCount;
    private Long commentCount;

    @Builder
    public BoardCountDto(Long postCount, Long commentCount) {
        this.postCount = postCount;
        this.commentCount = commentCount;
    }
}
