package com.testcode.yjp.last.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column
    private String user_id;

    @Column
    private String user_password;

    @Column
    private String user_name;

    @Column
    private int user_pn;

    @Column
    private String user_email;

    private int user_addr;

    private String user_addr2;

    private String user_addr3;

    private String user_rrn;

    @Builder
    public Member(Long id, String user_id, String user_password, String user_name, int user_pn, String user_email, int user_addr, String user_addr2, String user_addr3,String user_rrn) {
        this.id = id;
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_pn = user_pn;
        this.user_email = user_email;
        this.user_addr = user_addr;
        this.user_addr2 = user_addr2;
        this.user_addr3 = user_addr3;
        this.user_rrn = user_rrn;
    }

//    public void update(String user_password,String user_name, int user_pn,String user_email){
//        this.user_password = user_password;
//        this.user_name = user_name;
//        this.user_pn = user_pn;
//        this.user_email = user_email;
//    }


}
