package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.domain.dto.TrainerSearchDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/ptUser")
public class AndroidPTUserController {
    private final PTUserService ptUserService;
    private final PTUserRepository ptUserRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/myMembers/select/{id}")
    public List<PTUserApplyMemberDto> myMembersSelect(@PathVariable("id") Long id) {
        log.info("myMembersSelect in, id = " + id);

        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        Long tId = trainer_id.getId();
        log.info("myMembersSelect in, tId = " + tId);

        return ptUserRepository.findByUser(tId).stream()
                .map(PTUserApplyMemberDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/myTrainer/select/{id}")
    public TrainerInfoDto myTrainerSelect(@PathVariable("id") Long id) {
        log.info("myTrainerSelect in, id = " + id);
        PTUser checkApply = ptUserRepository.findCheckApply(id);

        TrainerInfo trainer_id = checkApply.getTrainer_id();
        TrainerInfoDto trainerInfoDto = new TrainerInfoDto(trainer_id);

        return trainerInfoDto;
    }

    @PostMapping("/trainer/search")
    public List<TrainerInfoDto> trainerSearch(@RequestBody TrainerSearchDto trainerSearchDto) {
        String search = trainerSearchDto.getSearch();
        String head = trainerSearchDto.getHead();

        List<TrainerInfo> trainerInfos = new ArrayList<>();
        try {
            if (head.equals("s")) {
                trainerInfos = trainerRepository.findSNS(search);
            } else if (head.equals("n")) {
                trainerInfos = trainerRepository.findTrainerName(search);
            } else if (head.equals("g")) {
                trainerInfos = trainerRepository.findGymName(search);
            } else if (head.equals("i")) {
                long id = Long.parseLong(search);
                TrainerInfo trainerInfo = trainerRepository.findById(id).get();
                trainerInfos.add(trainerInfo);
            } else {
                trainerInfos = trainerRepository.findAll();
            }
        } catch (Exception e) {
            trainerInfos = trainerRepository.findAll();
        }

        List<TrainerInfoDto> trainerInfoDtos = new ArrayList<>();
        for (TrainerInfo t : trainerInfos) {
            TrainerInfoDto trainerInfoDto = new TrainerInfoDto(t);
            trainerInfoDtos.add(trainerInfoDto);
        }
        return trainerInfoDtos;
    }
}