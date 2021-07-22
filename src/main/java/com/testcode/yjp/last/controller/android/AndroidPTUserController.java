package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.BuyerPt;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.domain.dto.android.AndPTUserApplyMemberDto;
import com.testcode.yjp.last.repository.BuyerPTRepository;
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
    private final BuyerPTRepository buyerPTRepository;


    @PostMapping("/myMembers/select/{id}")
    public List<PTUserApplyMemberDto> myMembersSelect(@PathVariable("id") Long id) {
        log.info("myMembersSelect in, id = " + id);

        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        Long tId = trainer_id.getId();
        log.info("myMembersSelect in, tId = " + tId);
        List<PTUserApplyMemberDto> ptUserApplyMemberDtos = ptUserRepository.findByUser(tId).stream()
                .map(PTUserApplyMemberDto::new)
                .collect(Collectors.toList());
        log.info("myMembersSelect in, tId = " + ptUserApplyMemberDtos);
        return ptUserApplyMemberDtos;
    }

    @PostMapping("/myTrainer/select/{id}")
    public PTUserApplyMemberDto myTrainerSelect(@PathVariable("id") Long id) {
        log.info("myTrainerSelect in, id = " + id);
        Member member = memberRepository.findById(id).get();
        PTUser checkApply = ptUserRepository.findCheckApply0or1(member);

        try {
            return new PTUserApplyMemberDto(checkApply);
        } catch (Exception e) {
            return new PTUserApplyMemberDto();
        }
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
    public ArrayList<AndPTUserApplyMemberDto> applyMember(@PathVariable("id") Long id) {
        log.info("applyMember" + id.toString());
        ArrayList<PTUser> apply = ptUserRepository.findApply(id);
        ArrayList<AndPTUserApplyMemberDto> andPTUserApplyMemberDtos = new ArrayList<>();
        for (PTUser p : apply) {
            Member member_id = p.getMember_id();
            AndPTUserApplyMemberDto a = new AndPTUserApplyMemberDto(member_id);
            log.info("a.getUser_id = " + a.getUser_id());
            andPTUserApplyMemberDtos.add(a);
        }
        return andPTUserApplyMemberDtos;
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

    @PostMapping(value = {"/apply/success/{id}/{trainer_id}"})
    public PTMemberInfoDto trainerApply(@PathVariable("id") Long member_id, @PathVariable("trainer_id") Long trainer_id, @RequestBody PTMemberInfoDto ptMemberInfoDto) {
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
        ptMemberInfoDto.setPt_end(0);

        ptUserService.save(ptMemberInfoDto);

        return ptMemberInfoDto;
    }

    @PutMapping("/buyer/update/{member_id}/{trainer_id}")
    public void buyerUpdate(@PathVariable("member_id") Long mid, @PathVariable("trainer_id") Long tid, @RequestBody String merchant_uid) {
        merchant_uid = merchant_uid.replaceAll("\\\"", "");
        log.info(mid + tid + merchant_uid);
        BuyerPt byMerchantUid = buyerPTRepository.findByMerchantUid(merchant_uid);
        Member member = memberRepository.findById(mid).get();
        TrainerInfo trainerInfo = trainerRepository.findById(tid).get();
        byMerchantUid.setMember(member);
        byMerchantUid.setTrainerInfo(trainerInfo);

        buyerPTRepository.save(byMerchantUid);
    }
}