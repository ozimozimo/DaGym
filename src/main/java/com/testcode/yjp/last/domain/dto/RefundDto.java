package com.testcode.yjp.last.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefundDto {

    private String merchant_uid;
    private String cancel_request_amount;
    private Long member_id;
    private Long trainer_id;


//         "refund_holder": "홍길동", // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
//                 "refund_bank": "88" // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(ex. KG이니시스의 경우 신한은행은 88번)
//                 "refund_account": "56211105948400" // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
}
