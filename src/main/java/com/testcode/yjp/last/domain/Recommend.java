package com.testcode.yjp.last.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hb_recommend")
public class Recommend {

    // 회원이 해야하구요 회원이 여러 게시글에 따봉을 할수있구요 ,  Long board_id 값이 두번안대요

    // 중복 추천 체크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;


    // 아이디당 1번만 추천이 가능함
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 게시판글에 대해서 1번만 추천이 가능함
    @ManyToOne
    @JoinColumn(name = "hb_num")
    private Board board;

    public Recommend(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

}

