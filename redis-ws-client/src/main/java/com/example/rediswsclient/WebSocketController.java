package com.example.rediswsclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketController {
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }
}
