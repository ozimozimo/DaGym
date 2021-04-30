package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndMemberFindPwDto {
    private String user_name;
    private String user_pn;
    private String user_id;

    public AndMemberFindPwDto(String user_name, String user_pn, String user_id) {
        this.user_name = user_name;
        this.user_pn = user_pn;
        this.user_id = user_id;
    }
}
