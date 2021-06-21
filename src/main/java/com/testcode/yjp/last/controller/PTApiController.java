package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.PTMemberInfoDto;
import com.testcode.yjp.last.domain.dto.PTUserApplyConDto;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ptUser")
public class PTApiController {
    private final MemberRepository memberRepository;
    private final PTUserRepository ptUserRepository;
    private final PTUserService ptUserService;
    private final TrainerRepository trainerRepository;

    //신청 버튼 누르면 db에 ptUser 저장하는 컨트롤러
    @PostMapping(value = {"/apply/success/{member_id}/{trainer_id}"})
    public PTMemberInfoDto trainerApply(@PathVariable("member_id") Long member_id, @PathVariable("trainer_id") Long trainer_id, @RequestBody PTMemberInfoDto ptMemberInfoDto) {
        log.info("pt 신청하는 member_id" + member_id);
        log.info("pt 신청받는 trainer_id" + trainer_id);
        log.info("postMapping post info 정보들 =" + ptMemberInfoDto);
        // 멤버 정보
        Optional<Member> memberId = memberRepository.findById(member_id);

        // pt 받게될 트레이너 정보
        Optional<TrainerInfo> trainerId = trainerRepository.findById(trainer_id);

        log.info("succ = " + memberId.get().getId());
        ptMemberInfoDto.setMember(memberId.get());
        ptMemberInfoDto.setTrainer(trainerId.get());

        ptMemberInfoDto.setAccept_condition("0"); // 신청 - 보류상태로 전환.

        ptUserService.save(ptMemberInfoDto);

        return ptMemberInfoDto;
    }

    // 이미 신청했는지 확인하기
//    @GetMapping("/apply/check")
//    public ArrayList<PTUserApplyMemberDto> checkApply(@RequestParam Long member_id) {
//        log.info("ptUser checkApply Get Controller");
//        System.out.println("member_id = " + member_id);
//        ArrayList<PTUserApplyMemberDto> ptCheck = ptUserService.getCheckList(member_id);
//        log.info("checkApply value = " + ptCheck);
//        return ptCheck;
//    }

//     신청 내역 확인
    @GetMapping("/apply/findMember")
    public List<PTMemberInfoDto> findMember(@RequestParam Long member_id) {
        log.info("ptUser findMember Get Controller");

        System.out.println("트레이너 member_id = " + member_id);

        List<PTMemberInfoDto> ptUserApplies = ptUserService.getPTUserApply(member_id);

        log.info("applyMember value = " + ptUserApplies);
        return ptUserApplies;
    }

    // 수락, 거절 결정
    @PostMapping("/apply/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody PTUserApplyConDto ptUserApplyConDto) {
        log.info("ptlist update post controller");
        System.out.println("id = " + id);
        System.out.println("ptUserApply.getId() = " + ptUserApplyConDto.getId());
        System.out.println("ptUserApply.getApply_if() = " + ptUserApplyConDto.getApply_if());


        return ptUserService.update(id, ptUserApplyConDto);
    }
}