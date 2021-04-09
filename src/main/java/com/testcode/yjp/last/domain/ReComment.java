package com.testcode.yjp.last.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reCm_id")
    private Long id;

    private String re_user_id; // id 작성자

    private Long re_parentCoNum; // 상위 댓글

    private String re_comments; // 하위 댓글

    @ManyToOne
    @JoinColumn(name = "cm_id")
    private Comment comment;

}
