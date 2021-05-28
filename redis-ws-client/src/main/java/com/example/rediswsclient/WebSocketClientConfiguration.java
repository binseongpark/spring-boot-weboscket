package com.example.rediswsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
public class WebSocketClientConfiguration {
    // @Autowired
    // WebSocketConnection webSocketConnection;
    @Autowired
    StompClient stompClient;

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient,
                                                     StompSessionHandler stompSessionHandler) throws ExecutionException, InterruptedException {
        // webSocketStompClient.setMessageConverter(new StringMessageConverter());


        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
//        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
//        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        webSocketStompClient.setMessageConverter(new StringMessageConverter());

        StompSession session = webSocketStompClient.connect("http://localhost:9000/ws", stompSessionHandler).get();

        stompClient.setSession(session);

        return webSocketStompClient;

        // return webSocketConnection.connection();
        // return WebSocketConnection.connection();
    }

    @Bean
    public static WebSocketClient webSocketClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }

    @Bean
    public static StompSessionHandler stompSessionHandler() {
        return new WebSocketEventListener();
    }
}
