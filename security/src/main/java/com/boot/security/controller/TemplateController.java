package com.boot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping("login")
    public String getLoginView() {
        System.out.println("VAO DAY");
        return "login";
    }

    //redirect after login success
    @GetMapping("course")
    public String getCourse() {
        return "course";
    }
}
