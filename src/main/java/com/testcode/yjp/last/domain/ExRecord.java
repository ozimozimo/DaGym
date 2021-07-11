package com.testcode.yjp.last.domain;

import com.testcode.yjp.last.domain.dto.android.AndExerciseRecordDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "exrecord")
public class ExRecord extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ex_record_id")
    private Long ex_record_id;
    private String ex_set; // 세트
    private String ex_weight; // KG
    private String ex_count; // 렙
    private String ex_date; // 운동한 날짜
    private String ex_time; // 운동한 시간
    private String ex_meter; // KM
    private String ex_parts;
    private String ex_name;
    private String kcal;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ex_id")
    private Exercise exercise;

}
