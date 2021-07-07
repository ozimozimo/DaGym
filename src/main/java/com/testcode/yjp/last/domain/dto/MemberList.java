package com.testcode.yjp.last.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberList {

    private Long id;
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
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder
    public MemberList(Long id, String user_id, String user_pw, String user_name, String user_pn, String user_email, String address_normal, String address_detail, String user_rrn, String user_gender, String user_role, LocalDateTime regDate) {
        this.id = id;
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
        this.regDate = regDate;
    }

}
