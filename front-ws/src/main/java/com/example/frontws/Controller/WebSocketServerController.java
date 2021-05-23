package com.example.frontws.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketServerController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

//    @SubscribeMapping("/ping")
//    public String listOfRoom() {
//        System.out.println("pong");
//        return "pong😘";
//    }
}
