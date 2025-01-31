package com.springSecurityTemplate.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String greet(HttpServletRequest httpServletRequest){
        return "Hello World " + httpServletRequest.getSession().getId();
    }

    @GetMapping("/about")
    public String about(HttpServletRequest httpServletRequest){
        return "Demo Security " + httpServletRequest.getSession().getId();
    }
}