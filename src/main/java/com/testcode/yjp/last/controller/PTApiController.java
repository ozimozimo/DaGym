package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.PTUserService;
import com.testcode.yjp.last.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final TrainerService trainerService;

    //신청 버튼 누르면 db에 ptUser 저장하는 컨트롤러
    @PostMapping(value = {"/apply/success/{member_id}/{trainer_id}"})
    public PTMemberInfoDto trainerApply(@PathVariable("member_id") Long member_id, @PathVariable("trainer_id") Long trainer_id, @RequestBody PTMemberInfoDto ptMemberInfoDto,
                                        HttpServletRequest request) {
        log.info("pt 신청하는 member_id : " + member_id);
        log.info("pt 신청받는 trainer_id : " + trainer_id);
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

    // pt 종료하기
    @PostMapping("/delete/{pt_id}")
    public void PTFinish(@PathVariable Long pt_id) {
        log.info("pt 종료 PK는 =" + pt_id);
        ptUserRepository.deletePT(pt_id);
    }

    // 회원이 신청한 목록 보기
    @GetMapping("/apply/memcheck")
    @ResponseBody
    public PTUser checkMemApply(@RequestParam Long member_id) {
        log.info("회원이 신청한 트레이너 조회 ");
        PTUser ptUser = ptUserService.getMemberApply(member_id);
        return ptUser;
    }

    // 이미 신청했는지 확인하기
    @GetMapping("/apply/check")
    @ResponseBody
    public PTUser checkApply(@RequestParam Long member_id) {
        log.info("ptUser checkApply Get Controller");
        System.out.println("member_id = " + member_id);
        PTUser checkList = ptUserService.getCheckList(member_id);
        return checkList;
    }

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
    @PostMapping("/apply/update/{pt_user_id}")
    public PTUserApplyConDto update(@PathVariable Long pt_user_id, @RequestBody PTUserApplyConDto ptUserApplyConDto) {
        System.out.println("ptUserApply.getApply_if() = " + ptUserApplyConDto.getApply_if());
        String data = ptUserApplyConDto.getApply_if();
        System.out.println("data = " + data);
        if (data.equals("1")) {
            ptUserService.update(pt_user_id, ptUserApplyConDto);
        } else if (data.equals("2")) {
            ptUserService.delete(pt_user_id);
        }
        System.out.println("ptuser_id =" + pt_user_id);
        return ptUserApplyConDto;
    }

    // 거절 하면서 데이터 삭제
//    url: '/ptUser/apply/delete/' + pt_user_id,
    @PostMapping("/apply/delete/{pt_user_id}")
    public Long delete(@PathVariable Long pt_user_id) {
        System.out.println("ptUser delete 삭제" + pt_user_id);
        ptUserService.delete(pt_user_id);
        return pt_user_id;
    }

    @GetMapping("/map/trainers")
    public List<TrainerInfoDto> trainer() {
        List<TrainerInfoDto> trainerLists = trainerService.findAll();
        System.out.println(trainerLists);

        return trainerLists;
    }

    @PostMapping("payment/{member_id}/{trainer_id}")
    public BuyerPTDto payResult(@PathVariable Long member_id,@PathVariable Long trainer_id, @RequestBody BuyerPTDto buyerPTDto) {

        log.info("결제 PK 아이디는 = " + member_id);
        ptUserService.payment(member_id,trainer_id,buyerPTDto);
        return buyerPTDto;
    }
}