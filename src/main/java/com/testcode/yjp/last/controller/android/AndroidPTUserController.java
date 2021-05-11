package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.android.*;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.repository.android.AndroidPTUserRepository;
import com.testcode.yjp.last.service.android.AndPTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/ptUser")
public class AndroidPTUserController {
    private final AndroidPTUserRepository androidPTUserRepository;
    private final AndroidMemberRepository androidMemberRepository;
    private final AndPTUserService andPTUserService;


    // 트레이너 검색
    @PostMapping("/search")
    public ArrayList<AndPTUserSearchDto> search(@RequestBody AndTrainerSearchDto trainerSearchDto) {
        String search = trainerSearchDto.getSearch();

        return andPTUserService.getTrainers(search);
    }

    // 트레이너 전체조회
    @GetMapping("/selectAll")
    public ArrayList<AndPTUserSearchDto> searchAll() {
        ArrayList<Member> trainerAll = androidPTUserRepository.findTrainerAll();

        return andPTUserService.getTrainers(trainerAll);
    }

    // 신청
    @PostMapping("/apply")
    public void applyTo(@RequestBody AndPTUserSaveDto andPTUserSaveDto) {
        String start_date = andPTUserSaveDto.getStart_date();
        String end_date = andPTUserSaveDto.getEnd_date();
        Long member_id = andPTUserSaveDto.getMember_id();
        Long trainer_id = andPTUserSaveDto.getTrainer_id();
        log.info("member_id = " + member_id + ", trainer_id = " + trainer_id + ", start_date = " + start_date + ", end_date = " + end_date);
        andPTUserService.extracted(member_id, trainer_id, andPTUserSaveDto);
//        return "success";
    }

    // 일반회원 -> 자기 트레이너 조회
    @GetMapping("/find/trainer/{member_id}")
    public AndMemberMypageDto selectTrainers(@PathVariable("member_id") Long member_id) {
        log.info("selectTrainers in + :" + member_id);
        return andPTUserService.getTrainers(member_id);
    }

    // 트레이너 -> 자기회원 조회
    @GetMapping("/find/member/{trainer_id}")
    public ArrayList<AndPTUserSearchDto> selectMembers(@PathVariable("trainer_id") Long trainer_id) {
        return andPTUserService.getMembers(trainer_id);
    }

    //신청 갯수
    @PostMapping("/apply/request")
    public int requestList(@RequestBody Long member_id) {
        Member member = androidMemberRepository.findById(member_id).get();
        ArrayList<PTUser> ptUsers = androidPTUserRepository.requestList(member);
        return ptUsers.size();
    }

    //신청온 회원 확인
    @PostMapping("/apply/findMember")
    public ArrayList<AndPTUserApplyMemberDto> applyMember(@RequestBody Long member_id) {
        ArrayList<AndPTUserApplyMemberDto> andPTUserApplyMemberDtos = andPTUserService.getAndPTUserSearchDtos(member_id);

        return andPTUserApplyMemberDtos;
    }

    @PutMapping("/apply/if")
    public void applyIf(@RequestBody AndPTUserApply andPTUserApply) {
        String user_id = andPTUserApply.getUser_id();
        Long trainer_id = andPTUserApply.getTrainer_id();
        String apply_if = andPTUserApply.getApply_if();
        log.info("member_id = " + user_id + "trainer_id = " + trainer_id);
        Member trainer = androidMemberRepository.findById(trainer_id).get();

        PTUser ptUserBy = androidPTUserRepository.findPTUserBy(user_id, trainer);
        if (apply_if.equals("수락")) {
            ptUserBy.setAccept_condition("1");
        } else if (apply_if.equals("거절")) {
            ptUserBy.setAccept_condition("2");
        }

        androidPTUserRepository.save(ptUserBy);
    }
}
