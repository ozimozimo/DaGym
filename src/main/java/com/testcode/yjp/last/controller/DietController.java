package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.dto.DietAddDto;
import com.testcode.yjp.last.service.DietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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

    @GetMapping("/list")
    public String list(String id, Model model) {
        log.info("result controller get");
        model.addAttribute("list", dietService.findAll(id));
        System.out.println("id = " + id);
        System.out.println(dietService.findAll(id));
        log.info("dietcontroller bbbbbbbbbbbb");
        return "diet/list";
    }

    @PostMapping("/save")
    public String save(@RequestBody DietAddDto dietAddDto, RedirectAttributes redirectAttributes) {
        dietAddDto.setDiet_id(1L);
        dietService.save(dietAddDto);
        redirectAttributes.addFlashAttribute("msg", "1");
        return "redirect:/diet/save";
    }

    @GetMapping("/listAll")
    public String list(String id, String modDate, Model model) {
        log.info("ID and ModDate");
        System.out.println("id = " + id);
        System.out.println("modDate = " + modDate);
        model.addAttribute("list", dietService.findByIdWithModDate(id, modDate));
        System.out.println("dietService.findByIdWithModDate(id, modDate) = " + dietService.findByIdWithModDate(id, modDate));
        log.info("holy moly");
        return "diet/list";
    }
}