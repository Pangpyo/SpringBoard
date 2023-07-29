package com.nts.board.comment.domain;

import com.nts.board.post.domain.Post;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
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

    @ManyToOne
    @JoinColumn(name = "post_pk")
    private Post posts;

}
