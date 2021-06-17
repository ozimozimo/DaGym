package com.testcode.yjp.last.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/adminMain")
    public String adminPage() {
        return "admin/adminMain";
    }

    @GetMapping("/userManagement")
    public String userManagement(Model model) {
        List<Member> members = adminService.selectUser();
        model.addAttribute("members", members);
        model.addAttribute("count", members.size());
        return "admin/userManagement";
    }

    @GetMapping("/trainerManagement")
    public String trainerManagement(Model model) {
        List<Member> members = adminService.selectTrainer();
        model.addAttribute("trainers", members);
        model.addAttribute("count", members.size());
        return "admin/trainerManagement";
    }



}
