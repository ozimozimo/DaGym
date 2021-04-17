package com.testcode.yjp.last.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
public class Comments extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "com_id")
    private Long id;

    @Column(name = "com_parentNum")
    private Long parentNum;

    @Column(name = "com_comments")
    private String comments;

    @Column(name = "com_name")
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "hb_num")
    private Board board;


    public void update(String comments ){
        this.comments = comments;
    }


}
