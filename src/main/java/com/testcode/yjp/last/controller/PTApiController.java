package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.PTUserApplyConDto;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ptUser")
public class PTApiController {
    private final MemberRepository memberRepository;
    private final PTUserRepository ptUserRepository;
    private final PTUserService ptUserService;

    //신청 버튼 누르면 db에 ptUser 저장하는 컨트롤러
    @PostMapping(value = {"/apply/success/{member_id}/{trainer_id}"})
    public PTUser trainerApply(@PathVariable("member_id") Long member_id, @PathVariable("trainer_id") Long trainer_id, @RequestBody PTUser ptUser) {
        log.info("postMapping IN");
        Optional<Member> memberId = memberRepository.findById(member_id);
        Optional<Member> trainerId = memberRepository.findById(trainer_id);

        log.info("succ = " + memberId.get().getId());
        ptUser.setMember_id(memberId.get());
        ptUser.setTrainer_id(trainerId.get());
        ptUser.setAccept_condition("0"); // 신청 - 보류상태로 전환.
        log.info("ptuser.get user = " + ptUser.getMember_id().getId());
        log.info("ptuser.get tr = " + ptUser.getTrainer_id().getId());
        log.info("ptuser.get ac = " + ptUser.getAccept_condition());
        ptUserRepository.save(ptUser);
        return ptUser;
    }

    // 이미 신청했는지 확인하기
    @GetMapping("/apply/check")
    public ArrayList<PTUserApplyMemberDto> checkApply(@RequestParam Long member_id) {
        log.info("ptUser checkApply Get Controller");
        System.out.println("member_id = " + member_id);
        ArrayList<PTUserApplyMemberDto> ptCheck = ptUserService.getCheckList(member_id);
        log.info("checkApply value = " + ptCheck);
        return ptCheck;
    }

    // 신청 내역 확인
    @GetMapping("/apply/findMember")
    public ArrayList<PTUserApplyMemberDto> findMember(@RequestParam Long trainer_id) {
        log.info("ptUser findMember Get Controller");
        System.out.println("trainer_id = " + trainer_id);
        ArrayList<PTUserApplyMemberDto> ptUserApplies = ptUserService.getPTUserApply(trainer_id);
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
        log.info("나와라요오오오");
        return ptUserService.update(id, ptUserApplyConDto);
    }
}