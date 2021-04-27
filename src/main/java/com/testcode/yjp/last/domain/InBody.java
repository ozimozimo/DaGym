package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
public class InBody extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inBody_id")
    private Long id;

    private String inBody_user_id;


    private String inBody_weight;

    //    기초대사량
    private String inBody_rmr;

    //    체지방률(%)
    // Precision : 소숫점을 포함한 전체 자리수
    // scale : 숫자 타입의 소수점 자릿수를 지정한다
    private String inBody_bfp;

    //    골격근량(kg)
    private String inBody_smm;

    private String inBody_date;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public InBody(Long id, String inBody_user_id, String inBody_weight, String inBody_rmr, String inBody_bfp, String inBody_smm,String inBody_date) {
        this.id = id;
        this.inBody_user_id = inBody_user_id;
        this.inBody_weight = inBody_weight;
        this.inBody_rmr = inBody_rmr;
        this.inBody_bfp = inBody_bfp;
        this.inBody_smm = inBody_smm;
        this.inBody_date = inBody_date;
    }

}
