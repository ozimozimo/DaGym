package com.testcode.yjp.last.domain.dto.android;

import com.testcode.yjp.last.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AndPTUserApplyMemberDto {
    private String user_name;
    private String user_id;

    public AndPTUserApplyMemberDto(Member member) {
        this.user_name = member.getUser_name();
        this.user_id = member.getUser_id();
    }
}
