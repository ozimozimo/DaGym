package com.testcode.yjp.last.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
@Table(name = "PT_USER")
public class PTUser extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pt_user_id")
    private Long id;

    // 키
    private String member_height;

    // 몸무게
    private String member_weight;

    // pt 목적
    private String pt_purpose;

    // pt 신청일
    private String pt_positiveDate;

    // 원하는 pt 시간대
    private String pt_wantTime;

    // 횟수
    private String pt_count;
    
    // member_id 기본정보에 + pt 추가 정보기입
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member_id;

    // pt 받게 될 트레이너 정보
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id")
    private TrainerInfo trainer_id;

    private String accept_condition; // {0 신청(보류), 1 신청(수락), 2 신청(거절)}로 생각

    public void update(Long id, String accept_condition) {
        this.id = id;
        this.accept_condition = accept_condition;
    }
}