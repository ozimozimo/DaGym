package com.testcode.yjp.last.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UploadTestController {

    @GetMapping("/uploadEx")
    public String uploadEx() {
        return "upload/uploadFile";
    }
}
