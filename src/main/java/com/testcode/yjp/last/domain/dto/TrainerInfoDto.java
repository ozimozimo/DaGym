package com.testcode.yjp.last.domain.dto;

import com.testcode.yjp.last.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerInfoDto {

    private Long id;

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
    private Member member;


}
