package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exrecord")
public class ExRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ex_record_id")
    private Long ex_record_id;
    private String ex_record_member_id;
    private String ex_name;
    private String ex_set;
    private String ex_weight;
    private String ex_count;
    private String ex_date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public ExRecord(Long ex_record_id, String ex_name, String ex_set, String ex_weight, String ex_count, String ex_date) {
        this.ex_record_id = ex_record_id;
        this.ex_name = ex_name;
        this.ex_set = ex_set;
        this.ex_weight = ex_weight;
        this.ex_count = ex_count;
        this.ex_date = ex_date;
    }
}
