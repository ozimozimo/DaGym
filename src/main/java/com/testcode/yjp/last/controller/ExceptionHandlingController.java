package com.testcode.yjp.last.controller;

import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class ExceptionHandlingController implements ErrorController {

    @RequestMapping("error")
    public String error(HttpServletRequest request, Model model) {

        Object status = request.getAttribute("javax.servlet.error.status_code");

        model.addAttribute("status", status);

//        model.addAttribute("status", "상태" + status);
        model.addAttribute("path", request.getAttribute("javax.servlet.error.request_uri"));
        model.addAttribute("timestamp", new Date());

        Object exceptionObj = request.getAttribute("javax.servlet.error.exception");
        if (exceptionObj != null) {
            Throwable e = ((Exception) exceptionObj).getCause();
            model.addAttribute("exception", e.getClass().getName());
            model.addAttribute("message", e.getMessage());
        }

//        if (status.equals(HttpStatus.SC_NOT_FOUND)) {
//            return "/error/404";
//        } else if (status.equals(405)) {
//            return "/error/405";
//        } else{
//            return "/error/500";
//        }
        return "error/errorPage";
    }

    @Override
    public String getErrorPath() {

        return null;
    }
}

