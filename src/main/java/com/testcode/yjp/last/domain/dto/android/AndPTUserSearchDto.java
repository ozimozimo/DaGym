package com.testcode.yjp.last.domain.dto.android;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndPTUserSearchDto {
    private Long id;
    private String user_name;
    private String user_id;
    private String user_email;
    private String user_pn;

    public AndPTUserSearchDto(Long id, String user_name, String user_id, String user_email, String user_pn) {
        this.id = id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pn = user_pn;
    }
}
