package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/trainer")
public class AndroidTrainerController {
    private final TrainerRepository trainerRepository;

    @PostMapping("/findTrainer/{id}")
    public TrainerInfoDto findTrainer(@PathVariable("id") Long id) {
        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        TrainerInfoDto trainerInfoDto = new TrainerInfoDto(trainer_id);

        return trainerInfoDto;
    }


    @PostMapping("/findTrainerStr/{id}")
    public String findTrainerStr(@PathVariable("id") Long id) {
        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        if (trainer_id != null) {
            return "true";
        } else {
            return "false";
        }
    }
}
