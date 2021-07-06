package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table
public class OneOnOne extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ooo_id")
    private Long id;
    @Column(name = "ooo_title")
    private String title;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "ooo_content" ,length = 2000)
    private String content;
    @Column(name = "ooo_answer" ,length = 2000)
    private String answer;

    private String category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
