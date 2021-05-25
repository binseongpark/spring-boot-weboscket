package com.example.rediswsclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSocketClientConfiguration {

    // @Autowired
    // WebSocketConnection webSocketConnection;

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient,
                                                     StompSessionHandler stompSessionHandler) {
        // webSocketStompClient.setMessageConverter(new StringMessageConverter());
        // webSocketStompClient.setMessageConverter(new
        // MappingJackson2MessageConverter());

        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        webSocketStompClient.connect("http://localhost:10000/ws", stompSessionHandler);
        
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
