package com.firstservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.swing.*;

@RestController
@Slf4j
@RequestMapping("/first-service/")
public class FirstServiceController {
    private final Environment env;

    @Autowired
    public FirstServiceController(Environment env){
        this.env = env;
    }

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
    public String check(HttpServletRequest request) {
        log.info("server prot = {}", request.getServerPort());
        return String.format("this is message from first service PORT %s", env.getProperty("local.server.port"));
    }
}
