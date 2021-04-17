package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"recommends"})
@Table(name = "health_board")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hb_num")
    private Long id;
    @Column(name = "hb_title")
    private String title;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "hb_content" ,length = 2000)
    private String content;


    @Column(name = "hb_hit")
    private int hit = 0;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private Set<Recommend> recommends = new HashSet<>();


    // 연관관계 메소드 다대일
//    public void addMember(Member member) {
//        member.getBoards().add(this);
//    }
    @Builder
    public Board(String title, String user_id, String content, int hit, Member member) {
        this.title = title;
        this.user_id = user_id;
        this.content = content;
        this.hit = hit;
        this.member = member;
    }

    @Builder
    public Board(String title, String content, String user_id, int hit) {
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.hit = hit;
    }




    public void update(String title, String content ){
        this.title = title;
        this.content = content;
    }
}
