package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Boastboard;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.BoardUpdateRequestDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.repository.BoastboardRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.BoastboardService;
import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boastboard")
public class BoastboardApiController {

    private final MemberRepository memberRepository;
    private final BoastboardRepository boastboardRepository;
    private final BoastboardService boastboardService;

    @PostMapping("/bbInsert/{member_id}")
    public void bbInsert(@PathVariable Long member_id, @RequestBody Boastboard boastboard) {
        Member member = memberRepository.findById(member_id).get();
        boastboard.setMember(member);
        boastboardRepository.save(boastboard);
    }

    @PutMapping("/bbUpdate/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                       @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto,
                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", pageRequestDto.getPage());
        return boastboardService.update(id, boardUpdateRequestDto);


    }

    @DeleteMapping("/bbDelete/{id}")
    public Long delete(@PathVariable Long id) {
        boastboardService.delete(id);
        return id;
    }
}
