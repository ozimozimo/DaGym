package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.android.AndTrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/trainer")
public class AndroidTrainerController {
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final AndTrainerService andTrainerService;

    @PostMapping("/trInfo/{id}")
    public TrainerInfoDto trInfo(@PathVariable Long id, @RequestBody TrainerInfoDto trainerInfoDto) {
        Optional<Member> byId = memberRepository.findById(id);
        trainerInfoDto.setId(id);
        trainerInfoDto.setMember(byId.get());

        andTrainerService.save(trainerInfoDto);

        return trainerInfoDto;
    }

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
