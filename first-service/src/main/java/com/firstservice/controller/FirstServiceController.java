package com.firstservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/first-service/")
public class FirstServiceController {
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome first";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        System.out.println(header); // 헤더 값 출력
        return "second service";
    }

    @GetMapping("/check")
    public String check() {
        return "this is message from first service";
    }
}
