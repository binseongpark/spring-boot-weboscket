package com.example.frontws.Controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends StompSessionHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        logger.info("Client connected: headers {}", headers);

//        session.subscribe("/chatpapp/subscribe", this);
//        session.subscribe("/queue/responses", this);
//        session.subscribe("/queue/errors", this);
//        session.subscribe("/topic/periodic", this);
//        session.subscribe("/chatapp/chat/rooms", this);
        session.subscribe("/chatapp/chat/rooms", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                logger.info("getPayloadType");
                return Object.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                logger.info(payload.toString());
                logger.info("handleFrame");
            }
        });

        session.subscribe("/room/123", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return Object.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                ObjectMapper objectMapper = new ObjectMapper();
                Base64.Decoder decoder = Base64.getDecoder();
                //                    byte[] decoded = Base64.getDecoder().decode(o);
//                    logger.info(Base64.getDecoder().decode(objectMapper.writeValueAsString(o)).toString());
                byte[] encoded = new byte[0];
                String stringEncoded = "";
                Map<String, String> map = new HashMap<>();
                try {
//                    map = objectMapper.convertValue(o, new HashMap<String, String>);
//                    stringEncoded = objectMapper.writeValueAsString(o);
                    objectMapper.writeValueAsBytes(o);
                    logger.info(new String(objectMapper.writeValueAsBytes(o)));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

//                    logger.info(Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(o).getBytes(StandardCharsets.UTF_8)));
//                    Base64.getDecoder().decode(objectMapper.writeValueAsString(o).getBytes());
//                    logger.info(Base64.getDecoder().decode(objectMapper.writeValueAsString(o).getBytes()));
            }
        });

        String message = "one-time message from client";
        logger.info("Client sends: {}", message);
        session.send("/app/request", message);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("Client received: payload {}, headers {}", payload, headers);
    }

    @Override
    public void handleException(StompSession session, StompCommand command,
                                StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Client error: exception {}, command {}, payload {}, headers {}",
                exception.getMessage(), command, payload, headers);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Client transport error: error {}", exception.getMessage());
    }
}
