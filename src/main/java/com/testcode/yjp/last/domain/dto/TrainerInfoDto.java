package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import lombok.*;

import javax.persistence.OneToOne;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerInfoDto {

    private Long id;

    private String user_id;

    private String user_name;

    private String user_pn;

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

    private Member member;


    public TrainerInfoDto(TrainerInfo entity){
        this.id = entity.getId();
        this.uuid = entity.getUuid();
        this.imgName = entity.getImgName();
        this.fileName = entity.getFileName();
        this.trainer_gymName = entity.getTrainer_gymName();
        this.trainer_category = entity.getTrainer_category();
        this.trainer_workTime = entity.getTrainer_workTime();
        this.trainer_address_normal = entity.getTrainer_address_normal();
        this.trainer_address_detail = entity.getTrainer_address_detail();
        this.trainer_instagram = entity.getTrainer_instagram();
        this.trainer_kakao = entity.getTrainer_kakao();
        this.trainer_content = entity.getTrainer_content();
        this.member = entity.getMember();
    }

}
