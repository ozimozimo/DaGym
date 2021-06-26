package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/memo")
    public String memo(Long calendar_user, Model model) {
        log.info("id =" + calendar_user);
        model.addAttribute("result", calendarService.findAll(calendar_user));
        return "calendar/calendar";
    }

    @GetMapping("/trMemo")
    public String trMemo(Long member_id, Long trainer_id, Model model) {
        log.info("member_id =" + member_id);
        log.info("trainer_id= " + trainer_id);
        model.addAttribute("member_id", member_id);
        model.addAttribute("trainer_id", trainer_id);

        return "calendar/ptCalendar";
    }

    @GetMapping("/memoPop")
    public String memoPop() {

        return "calendar/calendarInput";
    }

}
