package com.testcode.yjp.last.controller.android;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AndroidIamportTest {

    @RequestMapping(value="/orderCompleteMobile", produces = "application/text; charset=utf8", method = RequestMethod.GET)
    public String orderCompleteMobile(
             Model model
            , HttpSession session) {



        return "payTest";
    }

}
