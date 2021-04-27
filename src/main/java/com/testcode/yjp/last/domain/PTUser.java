package com.testcode.yjp.last.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "PT_USER")
public class PTUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member_id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Member trainer_id;

    private String user_id;
    private String user_name;
    private String user_pn;
    private String user_email;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String accept_condition; // {0 신청(보류), 1 신청(수락), 2 신청(거절)}로 생각

    @Builder
    public PTUser(Long id, Member member_id, Member trainer_id, String user_id, String user_name, String user_pn, String user_email, LocalDateTime start_date, LocalDateTime end_date, String accept_condition) {
        this.id = id;
        this.user_id = user_id;
        this.member_id = member_id;
        this.trainer_id = trainer_id;
        this.user_name = user_name;
        this.user_pn = user_pn;
        this.user_email = user_email;
        this.start_date = start_date;
        this.end_date = end_date;
        this.accept_condition = accept_condition;
    }
}
