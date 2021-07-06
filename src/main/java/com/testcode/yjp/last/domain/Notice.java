package com.testcode.yjp.last.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "notice")
public class Notice extends BaseEntity{
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
    @Column(name = "active")
    private int active = 0; // 0 = 비활성화, 1 = 활성화, 2 = FAQ


    @Column(name = "hb_hit")
    private int hit = 0;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
