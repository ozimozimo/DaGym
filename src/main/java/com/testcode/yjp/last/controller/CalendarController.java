package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.repository.PTUserRepository;
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
    private final PTUserRepository ptUserRepository;

    @GetMapping("/memo")
    public String memo(Long member_id, Long trainer_id, Model model) {
        model.addAttribute("result", calendarService.findAll(member_id,trainer_id));
        model.addAttribute("member_id", member_id);
        model.addAttribute("trainer_id", trainer_id);
        PTUser byInfo = ptUserRepository.findByInfo(member_id, trainer_id);
        model.addAttribute("count", byInfo.getPt_times());
        return "calendar/calendar";
    }

    @GetMapping("/trMemo")
    public String trMemo(Long member_id, Long trainer_id, Model model) {
        log.info("member_id =" + member_id);
        log.info("trainer_id= " + trainer_id);
        model.addAttribute("member_id", member_id);
        model.addAttribute("trainer_id", trainer_id);

        return "calendar/calendar";
    }

    @GetMapping("/memoPop")
    public String memoPop() {

        return "calendar/calendarInput";
    }

}
