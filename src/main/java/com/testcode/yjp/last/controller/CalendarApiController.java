package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.CalendarListDto;
import com.testcode.yjp.last.repository.CalendarRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Calendar save(@PathVariable Long id, @RequestBody Calendar calendar) {
        log.info("id는 " + id);
        log.info("Calendar controller Post");
        Optional<Member> result = memberRepository.findById(id);

        System.out.println(calendar.isAllDay());
        calendar.setMember(result.get());
        calendarRepository.save(calendar);
        return calendar;
    }

    @GetMapping("/findAll")
    public List<Calendar> findAll(Long id) {
        log.info("calendar findAll Controller");
        log.info("id=" + id);

        List<Calendar> all = calendarRepository.findAll(id);
        return all;
    }

    @PostMapping("/delete")
    public void delete(String calendar_start, String calendar_end) {
        System.out.println(calendar_start);
        System.out.println(calendar_end);
        calendarService.delete(calendar_start, calendar_end);
    }

    @PostMapping("/update")
    public void update(CalendarListDto calendarListDto) {
        log.info("calendarListDtoId" + calendarListDto.getId());
        calendarService.update(calendarListDto.getId(), calendarListDto);

    }

    @PostMapping("/timeUpdate")
    public void timeUpdate(CalendarListDto calendarListDto) {
        System.out.println(calendarListDto.getStart());
        System.out.println(calendarListDto.getEnd());
        System.out.println(calendarListDto.getId());
        calendarService.timeUpdate(calendarListDto);
    }

//    public String memo(Long id, Model model) {
//        log.info("id =" + id);
//        model.addAttribute("result", calendarService.findAll(id));
//        return "calendar/calendar";
//    }
}
