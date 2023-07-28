package com.nts.board.post.domain;

import com.nts.board.comment.domain.Comment;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postPk;

    @NotNull
    private String title;
    @NotNull
    private String postContent;
    @NotNull
    private String postAuthor;
    @ColumnDefault("0")
    private int hit;
    @NotNull
    private String password;

    @CreationTimestamp
    private Date createdAt;
    @CreationTimestamp
    private Date modifiedAt;
    @ColumnDefault("0")
    private int postLike;

    @ManyToMany
    @JoinTable(name = "post_hashtag")
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String postContent, String postAuthor, String password, List<Hashtag> hashtags) {
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.password = password;
        this.hashtags = hashtags;
    }

    @Builder
    public Post(Long postPk, String title, String postAuthor, int hit, Date createdAt, int postLike) {
        this.postPk = postPk;
        this.title = title;
        this.postAuthor = postAuthor;
        this.hit = hit;
        this.createdAt = createdAt;
        this.postLike = postLike;
    }

    public void update(String title, String postContent) {
        this.title = title;
        this.postContent = postContent;
        this.modifiedAt = new Date();
    }
}
