package com.nts.board.post.dao;

import com.nts.board.post.domain.Post;
import com.nts.board.post.dto.PostListResponseDto;
import com.nts.board.post.dto.SearchField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findAllBy(PageRequest pageRequest);

    Optional<Post> findById(Long postPk);

    @Modifying
    @Query("update Post p set p.hit = p.hit + 1 where p.postPk = :postPk")
    void updateHits(Long postPk);

    @Modifying
    @Query("update Post p set p.postLike = p.postLike + :likeValue where p.postPk = :postPk")
    void updateLikes(Long postPk, int likeValue);

}
