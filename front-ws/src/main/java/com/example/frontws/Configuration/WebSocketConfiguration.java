package com.example.frontws.Configuration;

import com.example.frontws.Controller.WebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
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
public class WebSocketConfiguration {
    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient,
                                                     StompSessionHandler stompSessionHandler) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
//        webSocketStompClient.setMessageConverter(new StringMessageConverter());
//        webSocketStompClient.setMessageConverter(new SimpleMessageConverter());
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        webSocketStompClient.connect("http://localhost:8080/websocket-sockjs-stomp", stompSessionHandler);
        webSocketStompClient.connect("http://localhost:8080/usaskchat", stompSessionHandler);
        return webSocketStompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }

    @Bean
    public StompSessionHandler stompSessionHandler() {
        return new WebSocketHandler();
    }
}