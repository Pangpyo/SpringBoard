package com.nts.board.comment.dto;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentPk;
    private String commentAuthor;
    private String commentContent;
    private boolean isDeleted;
    private Date commentAt;

    @Builder
    public CommentResponseDto(Long commentPk, String commentAuthor, String commentContent, boolean isDeleted, Date commentAt) {
        this.commentPk = commentPk;
        this.commentAuthor = commentAuthor;
        this.commentContent = commentContent;
        this.isDeleted = isDeleted;
        this.commentAt = commentAt;
    }
}
