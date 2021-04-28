package com.testcode.yjp.last.domain.dto.android;

import com.testcode.yjp.last.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AndMemberMypageDto {
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_pn;
    private String user_email;
    private String address_normal;
    private String address_detail;
    private String user_rrn;
    private String user_gender;
    private String user_role;
    private LocalDateTime regDate, modDate;

    public Member toEntity(){
        return Member.builder()
                .user_id(user_id)
                .user_pw(user_pw)
                .user_name(user_name)
                .user_pn(user_pn)
                .user_email(user_email)
                .address_normal(address_normal)
                .address_detail(address_detail)
                .user_rrn(user_rrn)
                .user_gender(user_gender)
                .user_role(user_role)
                .build();
    }


    @Builder
    public AndMemberMypageDto(String user_id, String user_pw, String user_name, String user_pn, String user_email,  String address_normal, String address_detail,String user_rrn,String user_gender,String user_role){
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_pn = user_pn;
        this.user_email = user_email;
        this.address_normal = address_normal;
        this.address_detail = address_detail;
        this.user_rrn = user_rrn;
        this.user_gender = user_gender;
        this.user_role = user_role;
    }
}