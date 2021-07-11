package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.BuyerPt;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BuyerPTDto {
    private Long id;

    // 고유ID
    private String imp_uid;
    // 상점 거래 ID
    private String merchant_uid;
    //결제 방법
    private String pay_method;
    // pt 실제 결제되는 가격
    private String pt_amount;
    // 카드 승인번호
    private String apply_num;
    private LocalDateTime regDate;

    private int bt_cancel;

    private Member member;
    private TrainerInfo trainerInfo;

    public BuyerPTDto(BuyerPt entity) {
        this.id = entity.getId();
        this.imp_uid = entity.getImp_uid();
        this.merchant_uid = entity.getMerchant_uid();
        this.pay_method = entity.getPay_method();
        this.pt_amount = entity.getPt_amount();
        this.apply_num = entity.getApply_num();
        this.member = entity.getMember();
        this.trainerInfo = entity.getTrainerInfo();
        this.bt_cancel = entity.getBt_cancel();
        this.regDate = entity.getRegDate();
    }
}
