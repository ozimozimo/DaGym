package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerInfo save(TrainerInfoDto trainerInfoDto) {

        TrainerInfo trainerInfo = TrainerInfo.builder()
                .id(trainerInfoDto.getId())
                .uuid(trainerInfoDto.getUuid())
                .imgName(trainerInfoDto.getImgName())
                .trainer_category(trainerInfoDto.getTrainer_category())
                .trainer_workTime(trainerInfoDto.getTrainer_workTime())
                .trainer_address_normal(trainerInfoDto.getTrainer_address_normal())
                .trainer_address_detail(trainerInfoDto.getTrainer_address_detail())
                .trainer_instagram(trainerInfoDto.getTrainer_instagram())
                .trainer_kakao(trainerInfoDto.getTrainer_kakao())
                .trainer_content(trainerInfoDto.getTrainer_content())
                .member(trainerInfoDto.getMember())
                .build();

        return trainerRepository.save(trainerInfo);
    }
}
