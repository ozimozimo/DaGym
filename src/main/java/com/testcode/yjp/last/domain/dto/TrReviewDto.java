package com.testcode.yjp.last.domain.dto;


import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrReview;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrReviewDto {

    private Long id;
    private String content;
    private int score;
    private Member member_id;
    private TrainerInfo trainer_id;
    private LocalDateTime regDate;

    public TrReviewDto(TrReview entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.score = entity.getScore();
        this.member_id = entity.getMember_id();
        this.trainer_id = entity.getTrainer_id();
        this.regDate = entity.getRegDate();
    }

}
