package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.calendar;
import com.testcode.yjp.last.repository.CalendarRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/calendar")
public class CalendarApiController {

    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final CalendarService calendarService;

    @PostMapping("/save/{id}")
    public calendar save(@PathVariable Long id, @RequestBody calendar calendar) {
        log.info("id는 " + id);
        log.info("Calendar controller Post");
        Optional<Member> result = memberRepository.findById(id);

        calendar.setMember(result.get());
        calendarRepository.save(calendar);
        return calendar;
    }

    @GetMapping("/findAll")
    public List<calendar> findAll(Long id) {
        log.info("calendar findAll Controller");
        log.info("id=" + id);
        List<calendar> all = calendarRepository.findAll(id);
        return all;
    }

//    public String memo(Long id, Model model) {
//        log.info("id =" + id);
//        model.addAttribute("result", calendarService.findAll(id));
//        return "calendar/calendar";
//    }
}
