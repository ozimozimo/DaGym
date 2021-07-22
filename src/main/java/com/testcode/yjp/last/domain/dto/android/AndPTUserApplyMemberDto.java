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
    private String user_pn;
    private Long id;
    private Long ptUserId;

    public AndPTUserApplyMemberDto(Member member) {
        this.id = member.getId();
        this.user_name = member.getUser_name();
        this.user_id = member.getUser_id();
        this.user_pn = member.getUser_pn();
    }
}
