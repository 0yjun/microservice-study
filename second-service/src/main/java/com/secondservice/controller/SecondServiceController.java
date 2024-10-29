package com.secondservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/second-service")
public class SecondServiceController {
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome second";
    }

    
    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        System.out.println(header); // 헤더 값 출력
        return "second service";
    }

    @GetMapping("/check")
    public String check() {
        return "this is message from second service";
    }
}
