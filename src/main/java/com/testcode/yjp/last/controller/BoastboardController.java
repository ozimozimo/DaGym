package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.BoastboardResponseDto;
import com.testcode.yjp.last.domain.dto.CommentsListResponseDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.BoastboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boastboard")
public class BoastboardController {

    private final BoastboardService boastboardService;
    private final MemberRepository memberRepository;

    @GetMapping("/bb")
    public String BList(Model model, PageRequestDto pageRequestDto) {
        model.addAttribute("bb", boastboardService.findAllDesc());
        model.addAttribute("result", boastboardService.getList(pageRequestDto));
        model.addAttribute("PageRequestDto", pageRequestDto);
        return "boastboard/boastboard";
    }

    @GetMapping("/bbInsert")
    public String BInsert(Model model) {
        return "boastboard/boastboardInsert";
    }

    @GetMapping("/bbDetail")
    public String BDetail(Long bb_num ,Model model,@ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        model.addAttribute("bb", boastboardService.findById(bb_num));
        return "boastboard/boastboardDetail";
    }

    @GetMapping("/bbUpdate")
    public String BUpdate( Long bb_num, Model model, @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto) {
        BoastboardResponseDto dto = boastboardService.findById(bb_num);

        model.addAttribute("bb", dto);
        return "boastboard/boastboardUpdate";
    }

}
