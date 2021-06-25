package com.testcode.yjp.last.service;

import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .fileName(trainerInfoDto.getFileName())
                .trainer_category(trainerInfoDto.getTrainer_category())
                .trainer_gymName(trainerInfoDto.getTrainer_gymName())
                .trainer_workTime(trainerInfoDto.getTrainer_workTime())
                .trainer_pt_total(trainerInfoDto.getTrainer_pt_total())
                .trainer_address_normal(trainerInfoDto.getTrainer_address_normal())
                .trainer_address_detail(trainerInfoDto.getTrainer_address_detail())
                .trainer_instagram(trainerInfoDto.getTrainer_instagram())
                .trainer_kakao(trainerInfoDto.getTrainer_kakao())
                .trainer_content(trainerInfoDto.getTrainer_content())
                .member(trainerInfoDto.getMember())
                .build();

        return trainerRepository.save(trainerInfo);
    }


    public TrainerInfoDto findById(Long id) {
        TrainerInfo entity = trainerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다= id" + id));
        return new TrainerInfoDto(entity);
    }

    public TrainerInfo update(Long id, TrainerInfoDto trainerInfoDto) {

        log.info("update Service 입니다");
        TrainerInfo trainerInfo = trainerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다" + id));
        trainerInfo.update(trainerInfoDto);

        return trainerRepository.save(trainerInfo);
    }

    public List<TrainerInfoDto> findAll() {
        return trainerRepository.findAll().stream()
                .map(TrainerInfoDto::new)
                .collect(Collectors.toList());
    }
}
