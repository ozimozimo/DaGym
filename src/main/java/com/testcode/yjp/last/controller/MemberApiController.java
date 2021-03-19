package com.testcode.yjp.last.controller;


import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MailDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.MemberService;
//import com.testcode.yjp.last.service.SendEmailService;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
//    private final SendEmailService sendEmailService;


    @PostMapping("/api/idChk")
    public String IdChk(String user_id) throws Exception {
        System.out.println(user_id);
        String str = memberService.IdChk(user_id);
        log.info("아이디체크로 들어옴");
        return str;
    }

    /*인증번호 전달받는 컨트롤러*/
    @GetMapping("/sendSMS")
    public @ResponseBody
    String sendSMS(String phoneNumber) {
        log.info("핸드폰 인증 시작");
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        memberService.certifiedPhoneNumber(phoneNumber, numStr);
        return numStr;
    }

    //  아이디 찾기
    @PostMapping("/IdCheck")
    @ResponseBody
    public Member IdCheck(String user_name, String user_email) throws Exception {
        System.out.println(user_name);
        System.out.println(user_email);
        Member checkId = memberRepository.findCheckId(user_name, user_email);
        log.info("post IdCheck Controller");

        return checkId;
    }

//    // 비밀번호 찾기
//    // Email과 name의 일치여부를 check하는 컨트롤러
//    @GetMapping("/PwCheck")
//    public @ResponseBody
//    Map<String, Boolean> pw_find(String user_name, String user_email){
//        Map<String,Boolean> json = new HashMap<>();
//        boolean pwFindCheck = memberService.userEmailCheck(user_name,user_email);
//
//        System.out.println(pwFindCheck);
//        json.put("check", pwFindCheck);
//        return json;
//    }
//    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
//    @PostMapping("/findPw/sendEmail")
//    public @ResponseBody void sendEmail(String user_email, String user_name){
//        MailDto dto = sendEmailService.createMailAndChangePassword(user_email, user_name);
//        sendEmailService.mailSend(dto);
//    }
}
