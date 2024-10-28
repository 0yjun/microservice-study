package com.firstservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service/")
public class FirstServiceController {
    @GetMapping("/first")
    public String welcome(){
        return "first";
    }
}
