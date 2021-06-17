package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/trainer")
public class TrainerController {

    private final MemberRepository memberRepository;
    private final TrainerService trainerService;
    private final TrainerRepository trainerRepository;


    @GetMapping("/trainerJoin")
    public String trainerJoin(String id, Model model) {
        System.out.println(id+ "값 받아옴><");

        Long trainerId = memberRepository.trainerId(id);
        System.out.println(trainerId+"ehgusid는");
//        System.out.println(trainerId+"=============================");
        model.addAttribute("id", trainerId);

        return "join/trainerJoin";
    }


}
