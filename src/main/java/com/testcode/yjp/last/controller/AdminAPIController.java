package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminAPIController {

    private final MemberRepository memberRepository;

//    @PostMapping("select")
//    public List<Member> selectUser() {
//        List<Member> memberAll = memberRepository.findAll();
//        return memberAll;
//
//    }
}
