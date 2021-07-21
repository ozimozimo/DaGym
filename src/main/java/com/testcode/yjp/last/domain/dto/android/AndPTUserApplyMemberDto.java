package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndPTUserApplyMemberDto {
    private Long id;
    private String user_name;
    private String user_id;
    private String start_date;
    private String end_date;

    public AndPTUserApplyMemberDto(Long id, String user_name, String user_id, String start_date, String end_date) {
        this.id = id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }


}
