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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long id;

    private String title;
    private String startDate;
    private String endDate;
    private String description;
    private String type;
    private String backgroundColor;
    private String textColor;
    private boolean allDay;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
