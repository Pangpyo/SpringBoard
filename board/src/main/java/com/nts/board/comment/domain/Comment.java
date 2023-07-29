package com.nts.board.comment.domain;

import com.nts.board.post.domain.Post;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(exclude = "post")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentPk;
    @NotNull
    private String commentAuthor;
    @NotNull
    private String commentContent;

    @ColumnDefault("0")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDeleted;

    @CreationTimestamp
    private Date commentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_pk")
    private Post post;

    @Builder
    public Comment(Long commentPk, String commentAuthor, String commentContent, Long postPk) {
        this.commentPk = commentPk;
        this.commentAuthor = commentAuthor;
        this.commentContent = commentContent;
        this.post = Post.builder().postPk(postPk).build();
    }

    public void delete() {
        this.isDeleted = true;
    }

}
