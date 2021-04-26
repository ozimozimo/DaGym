package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "calendar_id")
    private Long id;

    // 보내는 날짜
    private String calendar_date;

    private String calendar_memo;

    private String color;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
