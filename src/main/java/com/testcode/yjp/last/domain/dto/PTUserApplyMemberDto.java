package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PTUserApplyMemberDto {
    private Long id;
    private String member_height;
    private String member_weight;
    private String pt_purpose;
    private String pt_positiveDate;
    private String pt_wantTime;
    private String pt_count;
    private int pt_times;
    private Member member;
    private TrainerInfo trainerInfo;
    private String accept_condition;


    public PTUserApplyMemberDto(PTUser ptUser) {
        this.id = ptUser.getId();
        this.member_height = ptUser.getMember_height();
        this.member_weight = ptUser.getMember_weight();
        this.pt_purpose = ptUser.getPt_purpose();
        this.pt_positiveDate = ptUser.getPt_positiveDate();
        this.pt_wantTime = ptUser.getPt_wantTime();
        this.pt_count = ptUser.getPt_count();
        this.member = ptUser.getMember_id();
        this.pt_times = ptUser.getPt_times();
        this.trainerInfo = ptUser.getTrainer_id();
        this.accept_condition = ptUser.getAccept_condition();
    }




}
