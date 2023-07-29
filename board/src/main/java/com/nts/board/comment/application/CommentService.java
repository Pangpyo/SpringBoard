package com.nts.board.comment.application;

import com.nts.board.comment.domain.Comment;
import com.nts.board.comment.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.nts.board.comment.dao.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public Long addComment(Long postPk, CommentRequestDto commentRequestDto) {
        return commentRepository.save(Comment.builder()
                        .commentAuthor(commentRequestDto.getCommentAuthor())
                        .commentContent(commentRequestDto.getCommentContent())
                        .postPk(postPk)
                        .build()).getCommentPk();
    }
}
