package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.ExRecord;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRecordDto {
    private Long ex_record_id;
    private String ex_set; // 세트
    private String ex_weight; // KG
    private String ex_count; // 렙
    private String ex_date; // 운동한 날짜

    private String ex_time; // 운동한 시간
    private String ex_meter; // KM
    private Long ex_record_member_id;
    private Long exercise_id;
    private String ex_name;

    private String ex_category;
    private String ex_parts;
    private String kcal;

    public ExerciseRecordDto(ExRecord exRecord) {
        this.ex_record_id = exRecord.getEx_record_id();
        this.ex_set = exRecord.getEx_set();
        this.ex_weight = exRecord.getEx_weight();
        this.ex_count = exRecord.getEx_count();
        this.ex_date = exRecord.getEx_date();

        this.ex_time = exRecord.getEx_time();
        this.ex_meter = exRecord.getEx_meter();
        this.ex_record_member_id = exRecord.getMember().getId();
        this.exercise_id = exRecord.getExercise().getEx_id();
        this.ex_name = exRecord.getEx_name();

        this.ex_category = exRecord.getEx_count();
        this.ex_parts = exRecord.getEx_parts();
        this.kcal = exRecord.getKcal();
    }

    public ExRecord toEntity() {
        return ExRecord.builder()
                .ex_record_id(ex_record_id)
                .ex_set(ex_set)
                .ex_weight(ex_weight)
                .ex_count(ex_count)
                .ex_date(ex_date)
                .build();
    }


}
