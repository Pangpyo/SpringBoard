package com.nts.board.post.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

    @Builder
    public Post(String title, String postContent, String postAuthor, String password) {
        this.title = title;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.password = password;
    }

    public void update(String title, String postContent) {
        this.title = title;
        this.postContent = postContent;
        this.modifiedAt = new Date();
    }
}
