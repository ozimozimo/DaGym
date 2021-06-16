package com.testcode.yjp.last.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/trainer")
public class TrainerController {

    @GetMapping("/trainerJoin")
    public String trainerJoin() {
        return "join/trainerJoin";
    }
}