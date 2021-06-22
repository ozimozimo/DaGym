package com.testcode.yjp.last.controller;


import com.testcode.yjp.last.domain.InBody;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.repository.InBodyRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.InBodyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inBody")
@Slf4j
public class InBodyController {

    private final InBodyService inBodyService;
    private final MemberRepository memberRepository;
    private final InBodyRepository inBodyRepository;


    @GetMapping("/register")
    public String inBody() {
        return "inBody/inBodyRegister";
    }


    @GetMapping("/inBodyResult")
    public String inBodyResult(Long id,Model model) {
        System.out.println("member_id 는"+id);

        model.addAttribute("inBody",inBodyService.findAllDesc(id));
        return "inBody/inBodyResult";

    }


}
