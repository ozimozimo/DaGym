package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.domain.dto.TrainerSearchDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.PTReviewService;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/ptUser")
public class PTController {
    private final PTUserService ptUserService;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final PTUserRepository ptUserRepository;
    private final PTReviewService ptReviewService;

    @GetMapping("/ptBuyInfo")
    public String ptBuyInfo(Long member_id, Model model) {
        model.addAttribute("MembuyerInfo", ptUserService.getBuyInfo(member_id));
        model.addAttribute("TrbuyerInfo", ptUserService.getBuyMemInfo(member_id));
        return "ptUser/ptBuyInfo";
    }

    // 트레이너 조회, 검색 페이지 뷰
    @GetMapping("/view")
    public String trainerView(Model model,PageRequestDto pageRequestDto) {
        List<MemberList> memberLists = ptUserService.getMemberList();
//        List<Object[]> trainerLists = trainerRepository.getTrainerList();
        List<TrainerInfo> trainerLists = trainerRepository.findAll();

        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("result", ptUserService.getList(pageRequestDto));
        model.addAttribute("memberList", memberLists);
        model.addAttribute("trainerList", trainerLists);

        return "ptUser/trainerView";
    }

    @GetMapping("/view/map")
    public String mapView(Model model,PageRequestDto pageRequestDto){
        List<MemberList> memberLists = ptUserService.getMemberList();
//        List<Object[]> trainerLists = trainerRepository.getTrainerList();
        List<TrainerInfo> trainerLists = trainerRepository.findAll();

        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("result", ptUserService.getList(pageRequestDto));
        model.addAttribute("memberList", memberLists);
        model.addAttribute("trainerList", trainerLists);
        return "ptUser/trainerMapView";

    }


    @GetMapping("/view/detail/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        PTUser ptUser = ptUserRepository.findById(id).get();
        model.addAttribute("ptUsersInfo", ptUser);
        return "ptUser/userDetail";
    }


    // 매칭 되고 나서 회원은 한명의 트레이너를 볼수있다
    ///ptUser/mytrainer/(id=${session.loginUser})
    @GetMapping("/mytrainer")
    public String myTrainer(Long id, Model model) {

        System.out.println("myTrainer search get Controller");
        PTUser myTrainer = ptUserRepository.findMYTrainer(id);
        model.addAttribute("myTrainer",myTrainer);

        return "ptUser/myTrainer";
    }


//     해당 트레이너가 관리하는 회원들 목록 보여주는 뷰
    @GetMapping("/manage")
    public String acceptList(Model model, Long id) {

        System.out.println("trainer session login id = " + id);
        List<PTUserApplyMemberDto> acceptList = ptUserService.getAcceptList(id);
        model.addAttribute("acceptList", acceptList);
        System.out.println("acceptList = " + acceptList);
        return "ptUser/userManagement";
    }


//    @GetMapping("/userdetail")
//    public String PTUserDetail(Long id, Model model) {
//        log.info("유저 디테일 아디는=" + id);
//
//        Member memberInfo = memberRepository.findById(id).get();
//        model.addAttribute("member_info", memberInfo);
//        return "ptUser/userDetail";
//    }

    @GetMapping("/detail")
    public String trainerDetail(Long id, Model model, PageRequestDto pageRequestDto) {
        log.info("트레이너 디테일 아디는=" + id);
        log.info("트레이너 검색조건=" + pageRequestDto);

        TrainerInfo trainerInfo = trainerRepository.findById(id).get();
        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("trainerInfo", trainerInfo);
        model.addAttribute("reviewList", ptReviewService.trainerReviewList(id));

        return "ptUser/trainerDetail";
    }

    // 신청 페이지 / 유저 id(member_id), 트레이너 id(trainer_id) 받아서 일치하는 Member의 값
    // 보내줌. 시작날짜, 종료날짜는 달력 입혀줘야됨.
    // ※erdcloud에는 PtUser에 아이디값이 fk로 되있는데 엔티티 Long타입 시퀀스 아이디가 맞는거
    // 같아서 Long타입으로 받음.※
    @GetMapping("/apply")
    public String trainerApply(Model model, TrainerSearchDto trainerSearchDto) {
        Long member_id = trainerSearchDto.getMember_id();
        Long trainer_id = trainerSearchDto.getTrainer_id();
        log.info("controller get apply mem = " + member_id);
        log.info("controller get apply tr = " + trainer_id);



        List<MemberList> trainerLists = ptUserService.getMemberList(trainer_id);
        List<MemberList> memberLists = ptUserService.getMemberList(member_id);

        Member member = memberRepository.findById(trainerSearchDto.getMember_id()).get();
        TrainerInfo trainer = trainerRepository.findById(trainer_id).get();

        model.addAttribute("trainerList", trainerLists);
        model.addAttribute("memberList", memberLists);
        model.addAttribute("trainer_id", trainer_id);
        model.addAttribute("member", member);
        model.addAttribute("trainer", trainer);

        return "ptUser/trainerApply";
    }



}