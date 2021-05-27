package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.PTUser;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PTUserApplyMemberDto {
    private Long id;
    private String user_name;
    private String user_pn;
    private String user_id;
    private String start_date;
    private String end_date;
    private Long trainer_id;
    private String accept_condition;

    @Builder
    public PTUserApplyMemberDto(Long id, String user_name, String user_id, String start_date, String end_date, String accept_condition, Long trainer_id, String user_pn) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pn = user_pn;
        this.start_date = start_date;
        this.end_date = end_date;
        this.accept_condition = accept_condition;
        this.trainer_id = trainer_id;
    }
}
