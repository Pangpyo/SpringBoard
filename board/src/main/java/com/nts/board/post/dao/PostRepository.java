package com.nts.board.post.dao;

import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.PostListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new com.nts.board.post.dto.PostListResponseDto(postPk, title, createdAt, postAuthor, hit, postLike) from Post ORDER BY postPk DESC")
    List<PostListResponseDto> findAllByOrderByPostPkDesc();
}
