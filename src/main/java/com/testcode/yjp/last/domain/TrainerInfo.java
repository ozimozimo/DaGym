package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerInfo {

    @Id
    @Column(name = "trainer_id")
    private Long id;


    // 트레이너 이미지 변환
    private String uuid;

    // 트레이너 이미지
    private String imgName;

    // 트레이너 주특기
    private String trainer_category;
    // 근무시간
    private String trainer_workTime;
    // 헬스장 주소
    private String trainer_address_normal;
    // 헬스장 상세주소
    private String trainer_address_detail;
    // 트레이너 sns
    private String trainer_instagram;
    // 트레이너 sns
    private String trainer_kakao;
    // 트레이너 상세정보
    private String trainer_content;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;




}
