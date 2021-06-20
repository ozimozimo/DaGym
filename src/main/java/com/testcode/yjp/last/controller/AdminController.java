package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.NoticeRepository;
import com.testcode.yjp.last.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final NoticeRepository noticeRepository;

    @GetMapping("/adminMain")
    public String adminPage() {
        return "admin/adminMain";
    }

    @GetMapping("/userManagement")
    public String userManagement(Model model) {
        List<MemberList> memberLists = adminService.selectUser();
        log.info("memberLists = " + memberLists);
        model.addAttribute("members", memberLists);
        model.addAttribute("count", memberLists.size());
        model.addAttribute("tr_if", "user");

        return "admin/member/userManagement";
    }

    @GetMapping("/trainerManagement")
    public String trainerManagement(Model model) {
        List<MemberList> memberLists = adminService.selectTrainer();
        model.addAttribute("members", memberLists);
        model.addAttribute("count", memberLists.size());
        model.addAttribute("tr_if", "trainer");
        return "admin/member/userManagement";
    }

    @GetMapping("/boardManagement")
    public String boardManagement(Model model) {

        return "admin/board/boardManagement";
    }

    @GetMapping("/noticeManagement")
    public String noticeManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<NoticeDto, Notice> list = adminService.getList(pageRequestDto);
        model.addAttribute("notice", list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "admin/notice/noticeManagement";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model, Long id) {
        log.info("id = " + id);
        return "admin/notice/noticeDetail";
    }

    @GetMapping("/noticeInsertView")
    public String noticeInsert(Model model) {
        return "admin/notice/noticeInsert";
    }
}