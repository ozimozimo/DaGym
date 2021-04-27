package com.testcode.yjp.last.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reCm_id")
    private Long id;

    private String re_user_id; // id 작성자

    private Long re_parentCoNum; // 상위 댓글

    private Long re_parentNum; // 상위 게시글

    private String re_comments; //  댓글

    @ManyToOne
    @JoinColumn(name = "cm_id")
    private Comment comment;



}
