package com.testcode.yjp.last.service;


import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrReview;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.PTUserApplyMemberDto;
import com.testcode.yjp.last.domain.dto.TrReviewDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.TrReviewRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PTReviewService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final TrReviewRepository trReviewRepository;

    public void reviewRegister(Long member_id, Long trainer_id, TrReviewDto trReviewDto) {

        Optional<Member> member = memberRepository.findById(member_id);
        Optional<TrainerInfo> trainer = trainerRepository.findById(trainer_id);

        TrReview trReview = TrReview.builder()
                .score(trReviewDto.getScore())
                .content(trReviewDto.getContent())
                .member_id(member.get())
                .trainer_id(trainer.get())
                .build();

        trReviewRepository.save(trReview);
    }

    public List<TrReviewDto> trainerReviewList(Long trainer_id) {
        return trReviewRepository.reviewList(trainer_id).stream()
                .map(TrReviewDto::new)
                .collect(Collectors.toList());
    }

    public void modify(Long trReview_id, TrReviewDto trReviewDto) {
        TrReview trReview = trReviewRepository.findById(trReview_id).get();
        trReview.update(trReviewDto);
        trReviewRepository.save(trReview);
    }
}
