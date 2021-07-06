package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.OneOnOne;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.OooRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
@Slf4j
public class FAQApiController {

    private final MemberRepository memberRepository;
    private final OooRepository oooRepository;

    @PostMapping("/oooInsert/{id}")
    public void oooInsert(@PathVariable("id") Long id, @RequestBody OneOnOne oneOnOne) {
        Member member = memberRepository.findById(id).get();
        oneOnOne.setMember(member);
        oooRepository.save(oneOnOne);
    }
}
