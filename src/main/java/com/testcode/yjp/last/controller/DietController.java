package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Diet;
import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.domain.dto.DietDto;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/diet")
public class DietController {

    private final DietService dietService;

    @GetMapping("/search")
    public String search() {
        return "diet/search";
    }

    // 매개변수에서 Model model 삭제
    @GetMapping("/list")
    public String list(String id, Model model) {
        log.info("result controller get");
        model.addAttribute("list", dietService.findAll(id));
        System.out.println("id = " + id);
        System.out.println(dietService.findAll(id));
        log.info("dietcontroller bbbbbbbbbbbb");
        return "diet/list";
    }

//    @GetMapping("/list")
//    public List<DietDto> list(String id, String regDate) {
//        log.info("Today Data");
//        System.out.println("id = " + id);
//        System.out.println("regDate = " + regDate);
//        List<DietDto> byIdWithRegDate = dietService.findByIdWithRegDate(id, regDate);
//        System.out.println("byIdWithRegDate = " + byIdWithRegDate);
//        log.info("Today Data holy moly");
//        return byIdWithRegDate;
//    }

    @PostMapping("/save")
    public String save(@RequestBody DietAddDto dietAddDto, RedirectAttributes redirectAttributes) {
        dietAddDto.setDiet_id(1L);
        dietService.save(dietAddDto);
        redirectAttributes.addFlashAttribute("msg", "1");
        return "redirect:/diet/save";
    }

//    @GetMapping("/listAll")
//    public List<DietDto> list(String id, String modDate) {
//        log.info("ID and ModDate");
//        System.out.println("id = " + id);
//        System.out.println("modDate = " + modDate);
//        List<DietDto> result = dietService.findByIdWithModDate(id, modDate);
//        System.out.println("dietService.findByIdWithModDate(id, modDate) = " + dietService.findByIdWithModDate(id, modDate));
//        log.info("holy moly");
//        return result;
//    }

//    @GetMapping("/listAll")
//    public String list(String id, String modDate, Model model) {
//        log.info("ID and ModDate");
//        System.out.println("id = " + id);
//        System.out.println("modDate = " + modDate);
//        List<DietDto> byIdWithModDate = dietService.findByIdWithModDate(id, modDate);
//        model.addAttribute("list", byIdWithModDate);
//        System.out.println("dietService.findByIdWithModDate(id, modDate) = " + byIdWithModDate);
//
//        for(int i=0; i<byIdWithModDate.size();i++) {
//            log.info(": "+byIdWithModDate.get(i).getDiet_name());
//        }
//        log.info("holy moly");
//        return "diet/list";
//    }
}