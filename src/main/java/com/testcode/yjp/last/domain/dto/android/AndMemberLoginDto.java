package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndMemberLoginDto {
    private String user_id;
    private String user_pw;

    public AndMemberLoginDto(String user_id, String user_pw) {
        this.user_id = user_id;
        this.user_pw = user_pw;
    }
}
