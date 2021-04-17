package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.repository.DietRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@Log4j2
@RequestMapping("/diet")
public class DietController {

    private final DietService dietService;
    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;


    // 식단 페이지
//    @GetMapping("/list")
//    public String list() {
//        return "diet/list";
//    }

    @GetMapping("/search")
    public String search() {
        return "diet/search";
    }

    // 식단 내용 추가
    @PostMapping("/save")
    public String save(@RequestBody DietAddDto dietAddDto, RedirectAttributes redirectAttributes) {
//        log.info("잘넘어오나연 : " + dietAddDto);
//        log.info("dietAddDto dietresult : " + dietAddDto.getDiet_name()
//                + dietAddDto.getDiet_kcal() + dietAddDto.getDiet_carbo() + dietAddDto.getDiet_protein()
//                + dietAddDto.getDiet_fat());
        dietAddDto.setDiet_id(1L);
        dietService.save(dietAddDto);
        redirectAttributes.addFlashAttribute("msg", "1");
        return "redirect:/diet/save";
    }

    // 식단 추가 결과 list result로 바꿈
    @GetMapping("/list")
    public String list(String id, Model model){
        System.out.println(id);
        log.info("result controller get");
        model.addAttribute("list", dietService.findAll(id));
        System.out.println(dietService.findAll(id));
        return "diet/list";
    }
}
