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

    public void ExRecordUpdate(AndExerciseRecordDto exerciseRecordDto) {
        this.ex_name = exerciseRecordDto.getEx_name();
        this.ex_set = exerciseRecordDto.getEx_set();
        this.ex_weight =exerciseRecordDto.getEx_weight();
        this.ex_count = exerciseRecordDto.getEx_count();
        this.ex_date = exerciseRecordDto.getEx_date();
    }
}
