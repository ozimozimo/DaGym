package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.PTUserApplyConDto;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.domain.dto.TrainerSearchDto;
import com.testcode.yjp.last.domain.dto.android.AndPTUserApplyMemberDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public PTUserApplyMemberDto myTrainerSelect(@PathVariable("id") Long id) {
        log.info("myTrainerSelect in, id = " + id);
        PTUser checkApply = ptUserRepository.findCheckApply0or1(id);

        return new PTUserApplyMemberDto(checkApply);
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

    @PostMapping("/apply/member/{id}")
    public ArrayList<PTUserApplyMemberDto> applyMember(@PathVariable("id") Long id) {
        log.info("applyMember" + id.toString());
        List<PTUser> apply = ptUserRepository.findApply(id);
        try {
            log.info(apply.get(0).getId().toString());
        } catch (Exception e) {
        }
        ArrayList<PTUserApplyMemberDto> ptUserApplyMemberDtos = new ArrayList<>();

        for (PTUser p : apply) {
            PTUserApplyMemberDto ptUserApplyMemberDto = new PTUserApplyMemberDto(p);
            ptUserApplyMemberDtos.add(ptUserApplyMemberDto);
        }

        return ptUserApplyMemberDtos;
    }

    // 수락, 거절 결정
    @PostMapping("/apply/update/{pt_user_id}")
    public PTUserApplyConDto update(@PathVariable Long pt_user_id, @RequestBody PTUserApplyConDto ptUserApplyConDto) {
        System.out.println("ptUserApply.getApply_if() = " + ptUserApplyConDto.getApply_if());
        System.out.println("trainer member pK=" + ptUserApplyConDto.getTrainer_id());
        String data = ptUserApplyConDto.getApply_if();
        System.out.println("data = " + data);
        if (data.equals("1") || data.equals("2")) {
            ptUserService.update(pt_user_id, ptUserApplyConDto);
        }
        System.out.println("ptuser_id =" + pt_user_id);
        return ptUserApplyConDto;
    }
}