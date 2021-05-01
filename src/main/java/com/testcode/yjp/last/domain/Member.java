package com.testcode.yjp.last.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"boards"})
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

//     기본 전략은 LAZY
//    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
//    private List<Board> boards = new ArrayList<>();

    private String user_role;
//
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private Set<Likes> likes = new HashSet<>();
//
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private Set<Recommend> recommends = new HashSet<>();
//
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Member(Long id, String user_id, String user_pw, String user_name, String user_pn, String user_email,  String address_normal, String address_detail,String user_rrn,String user_gender,String user_role) {
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
    }


    public void update(String user_pw, String user_name, String user_pn, String user_email, String address_normal, String address_detail, String user_rrn,String user_gender, String user_role) {
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

    public void update(String user_pw, String user_name, String user_email, String address_normal, String address_detail, String user_role, String user_rrn) {
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_email = user_email;
        this.address_normal = address_normal;
        this.address_detail = address_detail;
        this.user_role = user_role;
        this.user_rrn = user_rrn;
    }


}
