package com.testcode.yjp.last.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PTUserApply {
    private Long id;
    private String user_name;
    private Long member_id;
    private String start_date;
    private String end_date;
    private Long trainer_id;
    private String accept_condition;

    @Builder
    public PTUserApply(Long id, String user_name, Long member_id, String start_date, String end_date, String accept_condition, Long trainer_id) {
        this.id = id;
        this.user_name = user_name;
        this.member_id = member_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.accept_condition = accept_condition;
        this.trainer_id = trainer_id;
    }
}
