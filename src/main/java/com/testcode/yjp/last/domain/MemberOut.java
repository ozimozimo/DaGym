package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberOut extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "memberout_id")
    private Long id;

    private Long out_id;

    private String out_comments;

}
