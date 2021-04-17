package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
@Table(name = "health_board")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hb_num")
    private Long id;
    @Column(name = "hb_title")
    private String title;
    @Column(name = "hb_content")
    private String content;
    @Column(name = "hb_author")
    private String author;

    @Column(name = "hb_hit")
    private int hit = 0;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    // 연관관계 메소드 다대일
//    public void addMember(Member member) {
//        member.getBoards().add(this);
//    }


    @Builder
    public Board(String title, String content, String author, int hit){
        this.title = title;
        this.content = content;
        this.author = author;
        this.hit = hit;
    }

    public void update(String title, String content ){
        this.title = title;
        this.content = content;
    }
}
