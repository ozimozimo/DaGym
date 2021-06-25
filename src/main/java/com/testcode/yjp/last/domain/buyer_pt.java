package com.testcode.yjp.last.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class buyer_pt extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "buyer_pt_id")
    private Long id;
    //결제 방법
    private String pay_method;
    // pt 횟수
    private String pt_count;

    // pt 실제 결제되는 가격
    private String pt_amount;
    // member에서 구매자 이메일, 전화번호, 주소, postcode가 들어간다
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
