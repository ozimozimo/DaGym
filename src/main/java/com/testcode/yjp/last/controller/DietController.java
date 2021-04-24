package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Log4j2
@RequestMapping("/diet")
public class DietController {

    private final DietService dietService;

    @GetMapping("/search")
    public String search() {
        return "diet/search";
    }

    // 식단 내용 추가
    @PostMapping("/save")
    public String save(@RequestBody DietAddDto dietAddDto, RedirectAttributes redirectAttributes) {
        dietAddDto.setDiet_id(1L);
        dietService.save(dietAddDto);
        redirectAttributes.addFlashAttribute("msg", "1");
        return "redirect:/diet/save";
    }

    // Model model
    @GetMapping("/list")
    public String list(String id, Model model) {
        System.out.println(id);
        log.info("result controller get");
        model.addAttribute("list", dietService.findAll(id));
        System.out.println(dietService.findAll(id));
        log.info("dietcontroller bbbbbbbbbbbb");
        return "diet/list";
    }

//    @GetMapping("/list/{id}/{modDate}")
//    public ResponseEntity<List<DietDto>> list(@PathVariable("id") String id, @PathVariable("modDate") String modDate) {
//        log.info("dietapicontrolleraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        log.info("잘넘어오나 보자 : " + id + " : " + modDate);
//        List<DietDto> modDateId = dietService.getDietListByModDate_Id(id, modDate);
//        log.info("잘 검색되나연 : " + modDateId);
//        return new ResponseEntity<>(modDateId, HttpStatus.OK);
//    }
}