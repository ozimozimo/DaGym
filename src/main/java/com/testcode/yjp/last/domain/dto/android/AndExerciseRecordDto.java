package com.testcode.yjp.last.domain.dto.android;

import com.testcode.yjp.last.domain.ExRecord;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AndExerciseRecordDto {
    private Long ex_record_id;
    private String ex_record_member_id;
    private String ex_name;
    private String ex_set;
    private String ex_weight;
    private String ex_count;
    private String ex_date;

    public AndExerciseRecordDto(ExRecord exRecord) {
        this.ex_record_id = exRecord.getEx_record_id();
        this.ex_record_member_id = exRecord.getEx_record_member_id();
        this.ex_name = exRecord.getEx_name();
        this.ex_set = exRecord.getEx_set();
        this.ex_weight = exRecord.getEx_weight();
        this.ex_count = exRecord.getEx_count();
        this.ex_date = exRecord.getEx_date();
    }

    public ExRecord toEntity() {
        return ExRecord.builder()
                .ex_record_id(ex_record_id)
                .ex_name(ex_name)
                .ex_set(ex_set)
                .ex_weight(ex_weight)
                .ex_count(ex_count)
                .ex_date(ex_date)
                .build();
    }

    @Builder
    public AndExerciseRecordDto(Long ex_record_id, String ex_name, String ex_set, String ex_weight, String ex_count, String ex_date) {
        this.ex_record_id = ex_record_id;
        this.ex_name = ex_name;
        this.ex_set = ex_set;
        this.ex_weight = ex_weight;
        this.ex_count = ex_count;
        this.ex_date = ex_date;
    }
}
