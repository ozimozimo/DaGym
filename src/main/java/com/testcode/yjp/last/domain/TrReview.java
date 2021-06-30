package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class TrReview extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "trReview_id")
    private Long id;

    // 리뷰에 들어갈 컬럼들

    // member_id 기본정보에 + pt 추가 정보기입
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member_id;

    // pt 받게 될 트레이너 정보
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private TrainerInfo trainer_id;
}
