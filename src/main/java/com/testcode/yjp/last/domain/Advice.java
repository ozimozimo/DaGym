package com.testcode.yjp.last.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Advice")
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "advice_id")
    private Long id;

    @Column(name = "advice_type")
    private String advice_type;
    // D 아니면 E

    @Column(name = "advice_content", length = 2000)
    private String advice_content;

    @Column(name = "advice_date")
    private String advice_date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member_id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Member trainer_id;

    @Builder
    public Advice(Long id, String advice_type, String advice_content, String advice_date, Member member_id, Member trainer_id) {
        this.id = id;
        this.advice_type = advice_type;
        this.advice_content = advice_content;
        this.advice_date = advice_date;
        this.member_id = member_id;
        this.trainer_id = trainer_id;
    }
}
