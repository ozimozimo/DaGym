package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Board;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.calendar;
import com.testcode.yjp.last.repository.CalendarRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/calendar")
public class CalendarApiController {

    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;

    @PostMapping("/save/{id}")
    public calendar save(@PathVariable Long id,@RequestBody calendar calendar) {
        log.info("id는 "+ id);
        log.info(calendar.getCalendar_memo());
        log.info(calendar.getCalendar_date());
        log.info("Calendar controller Post");
        Optional<Member> result = memberRepository.findById(id);

        calendar.setMember(result.get());
        calendarRepository.save(calendar);
        return calendar;
    }


}
