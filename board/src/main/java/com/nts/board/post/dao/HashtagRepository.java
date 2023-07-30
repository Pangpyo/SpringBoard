package com.nts.board.post.dao;

import com.nts.board.post.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    boolean existsByText(String text);
    Hashtag findByText(String text);
}
