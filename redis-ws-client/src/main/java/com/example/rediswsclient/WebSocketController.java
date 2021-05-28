package com.example.rediswsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Controller
public class WebSocketController {
//    @Autowired
//    StompSession session;
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//    @Autowired
//    WebSocketStompClient webSocketStompClient;
//    @Autowired
//    WebSocketStompClient webSocketStompClient;


    @Autowired
    StompClient stompClient;

    @GetMapping("/websocket/send")
    @ResponseBody
    public String test() throws InterruptedException {
//        String message = "dummy";
//        Object o = message;
//        webSocketStompClient.c
//        session.send("/ping-test", message);
//        stompClient.getSession().send("/pub/ping-test", o);
//        for (int i = 0; i < 1000; i++) {
//            stompClient.getSession().send("/pub/ping-test", message + " " + i);
//            Thread.sleep(10);
//        }
//        stompClient.getSession().send("/pub/ping-test", message);
//        messagingTemplate.convertAndSend("/ping-test", "dummy");

        SocketMessage message   = SocketMessage.builder().topic("log").message("log test").build();
        stompClient.getSession().send("/pub/message", Util.jacksonBeanToJson(message));

        message   = SocketMessage.builder().topic("notification").message("notification test").build();
        stompClient.getSession().send("/pub/message", Util.jacksonBeanToJson(message));
        return "test";
    }
}
