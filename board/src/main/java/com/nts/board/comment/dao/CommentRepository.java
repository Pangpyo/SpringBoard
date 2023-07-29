package com.nts.board.comment.dao;

import com.nts.board.comment.domain.Comment;
import com.nts.board.comment.dto.CommentResponseDto;
import com.nts.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, PageRequest pageRequest);
}
