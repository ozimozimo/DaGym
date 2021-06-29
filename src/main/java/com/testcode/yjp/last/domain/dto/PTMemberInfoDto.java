package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PTMemberInfoDto {

    private Long id;
    private String member_height;
    private String member_weight;
    private String pt_purpose;
    // 시작날짜 정하는거
    private String pt_count;
    //가능한 요일
    private String pt_positiveDate;
    //원하는 시간
    private String pt_wantTime;

    // PT 횟수
    private int pt_times;
    private Member member;
    private TrainerInfo trainer;
    private String accept_condition; // {0 신청(보류), 1 신청(수락), 2 신청(거절)}로 생각


    public PTMemberInfoDto(PTUser ptUser) {
        this.id = ptUser.getId();
        this.member_height = ptUser.getMember_height();
        this.member_weight = ptUser.getMember_weight();
        this.pt_times = ptUser.getPt_times();
        this.pt_purpose = ptUser.getPt_purpose();
        this.pt_count = ptUser.getPt_count();
        this.pt_positiveDate = ptUser.getPt_positiveDate();
        this.pt_wantTime = ptUser.getPt_wantTime();
        this.accept_condition = ptUser.getAccept_condition();
        this.member = ptUser.getMember_id();
        this.trainer = ptUser.getTrainer_id();
    }
}
