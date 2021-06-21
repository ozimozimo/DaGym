package com.testcode.yjp.last.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MemberFindIdDto;
import com.testcode.yjp.last.domain.dto.MemberJoinDto;
import com.testcode.yjp.last.domain.dto.MemberUpdate;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.MemberService;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PTUserService ptUserService;

    // 회원가입
    @GetMapping("/join")
    public String join() {
        return "join/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String save(MemberJoinDto memberJoinDto) {
        memberService.save(memberJoinDto);

        String member = memberJoinDto.getUser_role();
        System.out.println(member);

        String user_id = memberJoinDto.getUser_id();
        System.out.println(user_id + "====================================");
        if (member.equals("trainer")) {
            return "redirect:/trainer/trainerJoin?id="+user_id;
        }
        return "redirect:/member/login";
    }

    // 로그인 뷰
    @GetMapping("/login")
    public String login() {
        log.info("login page");
        return "login/loginView";
    }

    // 로그인 
    @PostMapping("/signIn")
    public String signIn(String user_id, String user_pw,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        Member member = memberRepository.findMember(user_id, user_pw);

        // pt 기간 만료
//        ptUserService.endDate(member);

        try {
            HttpSession session = (HttpSession) request.getSession();
            session.setAttribute("loginUser", member.getId());
            session.setAttribute("loginName", member.getUser_name());
            session.setAttribute("loginId", member.getUser_id());
            session.setAttribute("loginRole", member.getUser_role());
        } catch (NullPointerException n) {
            System.out.println(n);
            return "redirect:/member/login";
        }
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String signOut(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.removeAttribute("loginUser");
        session.invalidate();
        return "redirect:/";
    }

    // 회원조회
    @GetMapping("/mypage/mypageSelect")
    public String memberList(Model model) {
        log.info("드,ㄹ러옴");
        model.addAttribute("memberList", memberService.getMemberList());

        return "mypage/mypageSelect";
    }

    // 마이페이지 뷰
    @GetMapping("/mypage")
    public String memberUpdate(Long member_id, Model model) {
        MemberFindIdDto dto = memberService.findById(member_id);
        model.addAttribute("member", dto);

        return "mypage/mypageView";
    }

    // 마이페이지 
    @PostMapping("/mypage")
    public String update(Long member_id, MemberFindIdDto memberFindIdDto, HttpServletRequest request) {
        log.info("post mypage controller");
        System.out.println(member_id);
        memberService.update(member_id, memberFindIdDto);
        System.out.println(memberFindIdDto.getUser_pw());
        HttpSession session = (HttpSession) request.getSession();
        String user_role = memberFindIdDto.getUser_role();

        Member member = memberRepository.findById(member_id).get();

        String user_id = member.getUser_id();


        session.setAttribute("loginRole", user_role);

        if (user_role.equals("trainer")) {
            return "redirect:/trainer/trainerJoin?id="+user_id;
        }

        return "redirect:/";
    }

    // 소셜 마이페이지 추가 - 뷰
    @GetMapping("/mypage/social")
    public String socialUpdate(HttpServletRequest request, Model model) {
        HttpSession session = (HttpSession) request.getSession();
        Object member_id = session.getAttribute("loginUser");
        Long id = Long.valueOf(member_id.toString());
        log.info("소셜 member_id = " + id);
        MemberFindIdDto byId = memberService.findById(id);
        log.info("서비스 에서 컨트롤로 돌아옴.");

        try {
            String user_role = byId.getUser_role();
            if (user_role.equals("user") || user_role.equals("trainer")) {
                return "redirect:/";
            }
        } catch (NullPointerException e) {
            model.addAttribute("member", byId);
            return "mypage/mypageSocialView";
        }

        model.addAttribute("member", byId);
        return "mypage/mypageSocialView";
    }
    // 소셜 마이페이지 추가 - 업데이트 / 구상한거는 끝남.(비번이나 이메일, 이름, 전화번호 등의
    // 정보 입력은 추후에 바뀔 가능성 있음.
    @PostMapping("/mypage/social")
    public String socialUpdate(Long member_id, MemberFindIdDto memberFindIdDto, HttpServletRequest request) {
        System.out.println(member_id);
        log.info("social Controller. phonenum = " + memberFindIdDto.getUser_pn());
        memberService.update(member_id, memberFindIdDto);
        System.out.println(memberFindIdDto.getUser_pw());
        HttpSession session = (HttpSession) request.getSession();
        session.setAttribute("loginRole", memberFindIdDto.getUser_role());

        if (memberFindIdDto.getUser_role().equals("trainer")) {
            return "redirect:/trainer/trainerJoin?id="+memberFindIdDto.getUser_id();
        }

        return "redirect:/";
    }

    // ID PW 체크
    @GetMapping("/IdPwCheck")
    public String IdPwCheck(){
        return "login/IdPwCheck";
    }

    // 회원탈퇴
    @GetMapping("/memberOut")
    public String memberOut() {
        log.info("memberout get Controller");
        return "mypage/memberOut";
    }

}