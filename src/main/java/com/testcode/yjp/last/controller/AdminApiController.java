package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.domain.OneOnOne;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.NoticeRepository;
import com.testcode.yjp.last.repository.OooRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminApiController {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;
    private final OooRepository oooRepository;

    // 회원 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        log.info("id = " + id);
        memberRepository.deleteById(id);
    }

    // 회원 수정
    @PutMapping("/update/active/{id}/{active}")
    public void updateActive(@PathVariable("active") int active, @PathVariable("id") Long id) {
        Notice notice = noticeRepository.findById(id).get();
        notice.setActive(active);
        noticeRepository.save(notice);
    }

    // 게시판 삭제
    @DeleteMapping("/boardDelete/{id}")
    public void boardDelete(@PathVariable("id") Long id) {
        log.info("id = " + id);
        boardRepository.deleteById(id);
    }

    // 공지사항 등록
    @PostMapping("/noticeInsert/{id}")
    public void noticeInsert(@RequestBody Notice notice, @PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        notice.setMember(member);
        noticeRepository.save(notice);
    }

    // 공지사항 수정
    @PutMapping("/noticeUpdate/{id}")
    public void noticeUpdate(@PathVariable("id") Long id, @RequestBody Notice getNotice) {
        Notice notice = noticeRepository.findById(id).get();
        notice.setContent(getNotice.getContent());
        notice.setTitle(getNotice.getTitle());
        noticeRepository.save(notice);
    }

    // 공지사항 삭제
    @DeleteMapping("/noticeDelete/{id}")
    public void noticeDelete(@PathVariable("id") Long id) {
        noticeRepository.deleteById(id);
    }

    @PostMapping("/faqInsert/{id}")
    public void faqInsert(@PathVariable("id") Long id, @RequestBody Notice notice) {
        Member member = memberRepository.findById(id).get();
        notice.setMember(member);
        notice.setActive(2);
        noticeRepository.save(notice);
    }

    @PutMapping("/faqUpdate/{id}")
    public void faqUpdate(@PathVariable("id") Long id, @RequestBody Notice getNotice) {
        Notice notice = noticeRepository.findById(id).get();
        notice.setContent(getNotice.getContent());
        notice.setTitle(getNotice.getTitle());
        noticeRepository.save(notice);
    }

    @DeleteMapping("/faqDelete/{id}")
    public void faqDelete(@PathVariable("id") Long id) {
        noticeRepository.deleteById(id);
    }

    @PutMapping("/1on1Update/{id}")
    public void oooUpdate(@PathVariable("id") Long id, @RequestBody OneOnOne getOOO) {
        OneOnOne oneOnOne = oooRepository.findById(id).get();
        oneOnOne.setAnswer(getOOO.getAnswer());
        oooRepository.save(oneOnOne);
    }
}
