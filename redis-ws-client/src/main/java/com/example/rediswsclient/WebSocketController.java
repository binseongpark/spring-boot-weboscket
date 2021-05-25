package com.example.rediswsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketController {
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    StompClient stompClient;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
//        messagingTemplate.convertAndSend("/ping-test", "dummy");
        String message = "dummy";
//        stompClient.getSession().send("/ping-test", message);
        return "test";
    }
}
