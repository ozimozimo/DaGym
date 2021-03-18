package com.testcode.yjp.last.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
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

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private UserRole userRole;

    @Builder
    public Member(Long id, String user_id, String user_pw, String user_name, String user_pn, String user_email,  String address_normal, String address_detail,String user_rrn,String user_gender,UserRole userRole) {
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
        this.userRole = userRole;
    }

//    public void update(String user_password,String user_name, int user_pn,String user_email){
//        this.user_password = user_password;
//        this.user_name = user_name;
//        this.user_pn = user_pn;
//        this.user_email = user_email;
//    }


}
