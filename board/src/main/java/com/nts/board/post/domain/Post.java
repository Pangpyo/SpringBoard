package com.nts.board.post.domain;

import com.nts.board.comment.domain.Comment;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

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

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "post_hashtag",
            joinColumns = @JoinColumn(name = "postPk"),
            inverseJoinColumns = @JoinColumn(name = "hashtagPk"))
    private Set<Hashtag> hashtags = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    @Builder
    public Post(Long postPk, String title, String postContent, String postAuthor, int hit, String password, Date createdAt, Date modifiedAt, int postLike, Set<Hashtag> hashtags, List<Comment> comments) {
        this.postPk = postPk;
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.hit = hit;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postLike = postLike;
        this.hashtags = hashtags;
        this.comments = comments;
    }

    public void update(String title, String postContent, Set<Hashtag> hashtags) {
        this.title = title;
        this.postContent = postContent;
        this.hashtags = hashtags;
        this.modifiedAt = new Date();
    }
}
