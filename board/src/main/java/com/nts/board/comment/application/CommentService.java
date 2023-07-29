package com.nts.board.comment.application;

import com.nts.board.comment.domain.Comment;
import com.nts.board.comment.dto.CommentRequestDto;
import com.nts.board.comment.dto.CommentResponseDto;
import com.nts.board.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.nts.board.comment.dao.CommentRepository;

import javax.persistence.EntityNotFoundException;

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

    public Page<CommentResponseDto> findCommentList(Long postPk, int page) {
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("commentPk").descending());
        Page<Comment> comments = commentRepository.findByPost(Post.builder().postPk(postPk).build(), pageRequest);
        return comments.map(comment -> CommentResponseDto.builder()
                .commentPk(comment.getCommentPk())
                .commentAuthor(comment.getCommentAuthor())
                .commentContent(comment.isDeleted() ? "삭제된 메시지입니다" : comment.getCommentContent())
                .commentAt(comment.getCommentAt())
                .isDeleted(comment.isDeleted())
                .build());
    }

    public void removeComment(Long commentPk) {
        Comment comment = commentRepository.findById(commentPk)
                .orElseThrow(() -> new EntityNotFoundException());
        comment.delete();
        commentRepository.save(comment);
    }
}
