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
    public String memo(Long id, Model model) {
        log.info("id =" + id);
        model.addAttribute("result", calendarService.findAll(id));
        return "calendar/calendar";
    }

    @GetMapping("/memoPop")
    public String memoPop() {

        return "calendar/calendarInput";
    }

}
