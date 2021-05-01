package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Advice;
import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.repository.AdviceRepository;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.AdviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advice")
@Slf4j
public class AdviceApiController {

    private final AdviceRepository adviceRepository;
    private final AdviceService adviceService;
    private final DietRepository dietRepository;

    @PostMapping("/save/{advice_id}")
    public Advice adviceSave(@PathVariable("advice_id") Long id, @RequestBody Advice advice) {
        log.info("advice save Controller");
        System.out.println("advice.getMember_id() = " + advice.getMember_id());

        Optional<Diet> diet = dietRepository.findById(id);
        advice.setDiet(diet.get());
        adviceRepository.save(advice);

        return advice;
    }
}
