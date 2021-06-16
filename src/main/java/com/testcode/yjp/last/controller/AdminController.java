package com.testcode.yjp.last.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/adminMain")
    public String adminPage() {
        return "admin/adminMain";
    }

//    @GetMapping("select/User")
//    public String selectUser(Model model) {
//        return "";
//    }


}
