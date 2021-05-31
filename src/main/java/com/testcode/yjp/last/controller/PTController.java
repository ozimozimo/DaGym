package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.MemberList;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.TrainerSearchDto;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
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

    // 트레이너 조회, 검색 페이지 뷰
    @GetMapping("/view")
    public String trainerView() {
        return "ptUser/trainerView";
    }

    // 해당 트레이너가 관리하는 회원들 목록 보여주는 뷰
    @GetMapping("/manage")
    public String acceptList(Model model, @RequestParam(value="id") Long trainer_id) {
        System.out.println("trainer_id = " + trainer_id);
        ArrayList<PTUserApplyMemberDto> acceptList = ptUserService.getAcceptList(trainer_id);
        model.addAttribute("acceptList", acceptList);
        System.out.println("acceptList = " + acceptList);
        return "ptUser/userManagement";
    }

    // 트레이너 검색 (이름, 아이디, 헬스장 검색)
    @GetMapping("/search")
    public String search(Model model, TrainerSearchDto trainerSearchDto) {
        String search = trainerSearchDto.getSearch();
        String head = trainerSearchDto.getHead();
        log.info("controller Search = " + search);
        log.info("controller Head = " + head);

        List<MemberList> memberLists;

        if (head.equals("user_name")) {
            memberLists = ptUserService.nameSearch(search);
        } else if (head.equals("user_id")) {
            memberLists = ptUserService.idSearch(search);
        } else {
            memberLists = ptUserService.addrSearch(search);
        }

        model.addAttribute("memberList", memberLists);
        return "ptUser/trainerView";
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


        model.addAttribute("trainerList", trainerLists);
        model.addAttribute("memberList", memberLists);
        model.addAttribute("trainer_id", trainer_id);

        return "ptUser/trainerApply";
    }

    /* PTApiController에서 신청시 setAccept_condition("0")를 넣어줌.
    * accept_condition이 0일 경우에 트레이너에게 ptUser의 값이 보여짐(알림형식)
    * 거기서 트레이너가 수락 버튼을 누르면 accept_condition이 1이 되며
    * 트레이너의 내 회원에 accept_condition이 1이며 같이 매핑되있는 회원들이 보여짐.
    * 만약 거절 버튼을 누르면 accept_condition이 2가 되며 알림이 없어짐.
    * (근데 거절버튼 누르면 그냥 해당 로우를 삭제되게하면 안되려나 생각함.)
    * 그리고 현재 중복해서 신청하면 그대로 값 들어가는데 멤버id, 트레이너id 같으며
    * accept_condition이 0인 값 있으면 더 신청 못하게 막아야 할듯함.
    * */
}