package com.testcode.yjp.last.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"likes","reComments"})
@Table(name = "Reply")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cm_id")
    private Long id;

    @Column(name = "user_id")
    private String user_id;

    // 댓글 좋아요 수
    private int like_check=0;

    // 댓글 싫어요 수
    private int dislike_check=0;

    // board id
    private Long parentNum;

    @Column(name = "cm_comments", length = 2000)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "hb_num")
    private Board board;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "comment",orphanRemoval = true)
    private List<ReComment> reComments = new ArrayList<>();

    @Builder
    public Comment(String user_id, String comments, Long parentNum) {
        this.user_id = user_id;
        this.comments = comments;
        this.parentNum = parentNum;
    }


    public void update(String comments ){
        this.comments = comments;
    }
}
