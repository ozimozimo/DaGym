package com.testcode.yjp.last.controller;


import com.testcode.yjp.last.domain.dto.TrReviewDto;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.service.PTReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/pt")
public class PTReviewApiController {

    private final PTReviewService ptReviewService;
    private final PTUserRepository ptUserRepository;

    @PostMapping("/review/register/{member_id}/{trainer_id}")
    public TrReviewDto register(@PathVariable Long member_id, @PathVariable Long trainer_id, @RequestBody TrReviewDto trReviewDto) {
        log.info("review register post member_id=" + member_id);
        log.info("review register post trainer_id=" + trainer_id);
        log.info("review data = " + trReviewDto);
        ptReviewService.reviewRegister(member_id, trainer_id, trReviewDto);
        return trReviewDto;
    }

    @PostMapping("/review/modify/{trReview_id}")
    public void modify(@PathVariable Long trReview_id, @RequestBody TrReviewDto trReviewDto) {
        ptReviewService.modify(trReview_id, trReviewDto);
    }

    // 해당 트레이너의 리뷰 리스트
    @GetMapping("/review/list/{trainer_id}")
    public List<TrReviewDto> list(@PathVariable Long trainer_id) {
        List<TrReviewDto> trReviewDtos = ptReviewService.trainerReviewList(trainer_id);
        return trReviewDtos;
    }




}
