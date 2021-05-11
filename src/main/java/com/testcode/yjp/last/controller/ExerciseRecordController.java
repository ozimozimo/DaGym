package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.service.ExerciseRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ExRecord")
public class ExerciseRecordController {

    private final ExerciseRecordService exerciseRecordService;

    // 전체 조회
    @GetMapping("/exercise")
    public String exercise(String id, Model model) {
        log.info("ExRecord Controller Get");
        model.addAttribute("exercise", exerciseRecordService.findAll(id));
        System.out.println("id = " + id);
        System.out.println(exerciseRecordService.findAll(id));
        log.info("ExRecord Result Get");
        return "ExRecord/exercise";
    }

    // 저장
    @PostMapping("/save")
    public String save(@RequestBody ExerciseRecordDto exerciseRecordDto, RedirectAttributes redirectAttributes) {
        exerciseRecordDto.setEx_record_id(1L);
        exerciseRecordService.save(exerciseRecordDto);
        redirectAttributes.addFlashAttribute("msg", "1");
        return "redirect:/ExRecord/save";
    }
}
