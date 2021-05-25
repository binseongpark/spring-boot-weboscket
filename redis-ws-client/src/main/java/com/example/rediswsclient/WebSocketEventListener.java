package com.example.rediswsclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class WebSocketEventListener extends StompSessionHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    StompClient stompClient;

    public WebSocketEventListener() {
        super();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return super.getPayloadType(headers);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        super.handleFrame(headers, payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        super.afterConnected(session, connectedHeaders);

//        stompClient.setSession(session);

        session.subscribe("/sub/recv", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                logger.info("getPayloadType");
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                logger.info("recv!");
                logger.info(new String((byte[]) o));

                ObjectMapper objectMapper = new ObjectMapper();
                logger.info("recv!");
                logger.info(new String((byte[]) o));

//                messagingTemplate.convertAndSend("/recv", new String((byte[]) o));
            }
        });
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        super.handleException(session, command, headers, payload, exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        super.handleTransportError(session, exception);
    }
}
