package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.OneOnOne;
import com.testcode.yjp.last.domain.dto.NoticeDto;
import com.testcode.yjp.last.domain.dto.OooDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.domain.dto.PageResultDto;
import com.testcode.yjp.last.repository.NoticeRepository;
import com.testcode.yjp.last.repository.OooRepository;
import com.testcode.yjp.last.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/faq")
public class FAQController {
    private final AdminService adminService;
    private final NoticeRepository noticeRepository;
    private final OooRepository oooRepository;

    //FAQ
    @GetMapping("/faqManagement")
    public String faqManagement(Model model, PageRequestDto pageRequestDto) {
        PageResultDto<NoticeDto, Notice> list = adminService.getList(pageRequestDto, 2);
        model.addAttribute("notice", list);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "FAQ/faqManagement";
    }

    //FAQ
    @GetMapping("/oooManagement")
    public String oooManagement(Model model, PageRequestDto pageRequestDto, String category) {
        PageResultDto<OooDto, OneOnOne> oooList;
        oooList = adminService.getOooList(pageRequestDto, category);
        model.addAttribute("ooo", oooList);
        model.addAttribute("PageRequestDto", pageRequestDto);

        return "FAQ/oooManagement";
    }

    @GetMapping("/faqDetail")
    public String faqDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        Notice notice = noticeRepository.findById(id).get();
        model.addAttribute("faq", notice);
        return "FAQ/faqDetail";
    }

    @GetMapping("/oooDetail")
    public String oooDetail(Model model, Long id, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        OneOnOne oneOnOne = oooRepository.findById(id).get();
        model.addAttribute("ooo", oneOnOne);
        return "FAQ/oooDetail";
    }

    @GetMapping("/oooInsertView")
    public String oooInsert() {
        return "FAQ/oooInsert";
    }
}
