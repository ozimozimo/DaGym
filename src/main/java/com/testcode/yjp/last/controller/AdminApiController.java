package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.Notice;
import com.testcode.yjp.last.repository.BoardRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.NoticeRepository;
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

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        log.info("id = " + id);
        memberRepository.deleteById(id);
    }

    @PutMapping("/update/active/{id}/{active}")
    public void updateActive(@PathVariable("active") int active, @PathVariable("id") Long id) {
        Notice notice = noticeRepository.findById(id).get();
        notice.setActive(active);
        noticeRepository.save(notice);
    }

    @PostMapping("/noticeInsert/{id}")
    public void noticeInsert(@RequestBody Notice notice, @PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        notice.setMember(member);
        noticeRepository.save(notice);
    }

    @DeleteMapping("/boardDelete/{id}")
    public void boardDelete(@PathVariable("id") Long id) {
        log.info("id = " + id);
        boardRepository.deleteById(id);
    }

    @PutMapping("/noticeUpdate/{id}")
    public void noticeUpdate(@PathVariable("id") Long id, @RequestBody Notice getNotice) {
        Notice notice = noticeRepository.findById(id).get();
        notice.setContent(getNotice.getContent());
        notice.setTitle(getNotice.getTitle());
        noticeRepository.save(notice);
    }

    @DeleteMapping("/noticeDelete/{id}")
    public void noticeDelete(@PathVariable("id") Long id) {
        noticeRepository.deleteById(id);
    }
}
