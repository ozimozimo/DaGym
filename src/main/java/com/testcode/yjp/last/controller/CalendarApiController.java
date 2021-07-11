package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.CalendarListDto;
import com.testcode.yjp.last.repository.CalendarRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
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
    private final TrainerRepository trainerRepository;
    private final PTUserRepository ptUserRepository;

    @PostMapping("/save/{member_id}/{trainer_id}")
    public Calendar save(@PathVariable Long member_id, @PathVariable Long trainer_id, @RequestBody Calendar calendar, Model model) {
        log.info("Calendar controller Post");
        log.info("시작시간"+calendar.getStart());
        log.info("종료시간"+calendar.getEnd());
        Optional<Member> member = memberRepository.findById(member_id);
        Optional<TrainerInfo> trainer = trainerRepository.findById(trainer_id);
        calendar.setMember(member.get());
        calendar.setTrainerInfo(trainer.get());
        if (calendar.getType().equals("PT일정")) {
            ptUserRepository.update(member_id, trainer_id);
        }
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

    @GetMapping("/findPTAll")
    public List<Calendar> findPTAll(@RequestParam Long id) {
        log.info("trainer_id =" + id);
        List<Calendar> ptAll = calendarRepository.findPTAll(id);
        return ptAll;
    }

    @GetMapping("/findPT/{member_id}/{trainer_id}")
    public List<Calendar> findPT(@PathVariable Long member_id, @PathVariable Long trainer_id) {
        List<Calendar> all = calendarRepository.findPT(member_id, trainer_id);
        return all;
    }

    @PostMapping("/delete")
    public void delete(String calendar_start, String calendar_end, Long member_id, Long trainer_id) {
        System.out.println(calendar_start);
        System.out.println(calendar_end);
        System.out.println(member_id);
        System.out.println(trainer_id);



        ptUserRepository.Ptupdate(member_id, trainer_id);
        calendarService.delete(calendar_start, calendar_end,member_id,trainer_id);


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

    @PostMapping("/reSizeUpdate")
    public void reSizeUpdate(CalendarListDto calendarListDto) {
        System.out.println(calendarListDto.getStart());
        System.out.println(calendarListDto.getEnd());
        System.out.println(calendarListDto.getId());
        calendarService.reSizeUpdate(calendarListDto);
    }

//    public String memo(Long id, Model model) {
//        log.info("id =" + id);
//        model.addAttribute("result", calendarService.findAll(id));
//        return "calendar/calendar";
//    }
}
