package com.testcode.yjp.last.domain;

import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
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

    // 파일 이름
    private String fileName;

    // 트레이너 주특기
    private String trainer_category;
    // 근무시간
    private String trainer_workTime;
    // 헬스장 이름
    private String trainer_gymName;
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

    // 트레이너 PT 횟수/가격
    private String trainer_pt_total;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public void update(TrainerInfoDto trainerInfoDto) {
        this.uuid = trainerInfoDto.getUuid();
        this.imgName = trainerInfoDto.getImgName();
        this.fileName = trainerInfoDto.getFileName();
        this.trainer_gymName = trainerInfoDto.getTrainer_gymName();
        this.trainer_category = trainerInfoDto.getTrainer_category();
        this.trainer_workTime = trainerInfoDto.getTrainer_workTime();
        this.trainer_address_normal = trainerInfoDto.getTrainer_address_normal();
        this.trainer_address_detail = trainerInfoDto.getTrainer_address_detail();
        this.trainer_instagram = trainerInfoDto.getTrainer_instagram();
        this.trainer_kakao = trainerInfoDto.getTrainer_kakao();
        this.trainer_content = trainerInfoDto.getTrainer_content();
        this.trainer_pt_total = trainerInfoDto.getTrainer_pt_total();
    }
}
