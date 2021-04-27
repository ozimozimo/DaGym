package com.testcode.yjp.last.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ExRecord")
public class ExerciseRecordController {

    @GetMapping("/ExRecordSelect")
    public String ExRecord() {

        return "ExRecord/ExRecordSelect";
    }


}
