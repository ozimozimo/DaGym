package com.testcode.yjp.last.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {


    @GetMapping("/payTest")
    public String payTest() {
        return "payTest";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }


}
