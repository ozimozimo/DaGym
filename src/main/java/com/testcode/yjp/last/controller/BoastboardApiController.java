package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Boastboard;
import com.testcode.yjp.last.domain.Emotion;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.BoardUpdateRequestDto;
import com.testcode.yjp.last.domain.dto.EmoSelectDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.repository.BoastboardRepository;
import com.testcode.yjp.last.repository.EmotionRepository;
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

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boastboard")
public class BoastboardApiController {

    private final MemberRepository memberRepository;
    private final BoastboardRepository boastboardRepository;
    private final BoastboardService boastboardService;
    private final EmotionRepository emotionRepository;

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

    @PostMapping("/emotion/{member_id}/{bb_num}")
    public String emoInsert(@PathVariable("member_id") Long member_id, @PathVariable("bb_num") Long bb_num, @RequestBody Emotion emotion) {
        System.out.println("mem = " + member_id + " bb_num = " + bb_num + "emo = " + emotion.getEmotion());
        return boastboardService.saveEmo(member_id, bb_num, emotion);
    }

    @GetMapping("/emotion/select/{bb_num}")
    public EmoSelectDto emoSelect(@PathVariable("bb_num") Long bb_num) {
        Boastboard boastboard = boastboardRepository.findById(bb_num).get();
        List<Emotion> byEmotion = emotionRepository.findByEmotion(boastboard);
        int lCount=0;
        int mCount=0;
        int sCount=0;
        int aCount=0;
        int wCount=0;
        for (Emotion e : byEmotion) {
            if (e.getEmotion().equals("l")) {
                System.out.println("l = " + e.getEmotion());
                lCount++;
            } else if (e.getEmotion().equals("m")) {
                System.out.println("m = " + e.getEmotion());
                mCount++;
            }else if (e.getEmotion().equals("s")) {
                System.out.println("s = " + e.getEmotion());
                sCount++;
            }else if (e.getEmotion().equals("a")) {
                System.out.println("a = " + e.getEmotion());
                aCount++;
            }else if(e.getEmotion().equals("w")){
                System.out.println("emo = " + e.getEmotion());
                wCount++;
            }
        }
        EmoSelectDto emoSelectDto = EmoSelectDto.builder()
                .m(mCount)
                .a(aCount)
                .l(lCount)
                .s(sCount)
                .w(wCount)
                .build();
        return emoSelectDto;
    }
}
