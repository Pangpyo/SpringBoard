package com.nts.board.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hashtagPk;
    @Column(unique = true)
    private String text;

    @ManyToMany(mappedBy = "hashtags")
    private List<Post> posts;

    @Builder
    public Hashtag(String text) {
        this.text = text;
    }
}
