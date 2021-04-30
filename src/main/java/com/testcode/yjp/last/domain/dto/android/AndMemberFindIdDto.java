package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndMemberFindIdDto {
    private String user_name;
    private String user_pn;

    public AndMemberFindIdDto(String user_name, String user_pn) {
        this.user_name = user_name;
        this.user_pn = user_pn;
    }
}
