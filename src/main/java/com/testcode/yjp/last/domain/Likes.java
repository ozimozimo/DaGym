package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reply_likes")
public class Likes {

    // 중복 추천 체크
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "like_id")
    private Long id;


    // 아이디당 1번만 추천이 가능함
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 게시판글에 대해서 1번만 추천이 가능함
    @ManyToOne
    @JoinColumn(name = "hb_num")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "cm_id")
    private Comment comment;

    // 댓글 좋아요 수
    private int like_check=0;

    // 댓글 싫어요 수
    private int dislike_check=0;


    public Likes(Member member, Board board, Comment comment) {
        this.member = member;
        this.board = board;
        this.comment = comment;
    }

}
