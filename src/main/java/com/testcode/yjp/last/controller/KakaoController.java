package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MemberSoDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.MemberService;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequiredArgsConstructor
public class KakaoController {
    private final MemberService MemberService;
    private final MemberRepository memberRepository;
    private final PTUserService ptUserService;

    @RequestMapping(value = "/login/kakao")
    public String kakao(@RequestParam("userId") String user_id, @RequestParam("userEmail") String user_email,
                        @RequestParam("userNickName") String user_name, HttpServletRequest request) {
        log.info(user_id);
        log.info(user_email);
        log.info(user_name);
//        log.info(user_rrn);
//        log.info(user_gender);
//
//        if (user_gender.equals("male")) {
//            user_gender = "1";
//        } else {
//            user_gender = "2";
//        }
        MemberSoDto memberSoDto = new MemberSoDto(user_id, user_name, user_email);
        Member ckUserId = memberRepository.findByUser_id(user_id);
        if (ckUserId == null) {
            log.info("KakaoSave");
            MemberService.kakaoSave(memberSoDto);
        }
        ckUserId = memberRepository.findByUser_id(user_id);

        // pt 기간 만료
        ptUserService.endDate(ckUserId);

        HttpSession session = (HttpSession) request.getSession();
        session.setAttribute("loginId", ckUserId.getId());
        session.setAttribute("loginName", user_name);
        session.setAttribute("loginUser", ckUserId.getId()); // 추가한거
        session.setAttribute("loginRole", ckUserId.getUser_role());

        session.setAttribute("social","social");

        log.info("sendRedirect succ");

        return "redirect:/";
    }

}