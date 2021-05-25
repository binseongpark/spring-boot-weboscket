package com.example.rediswsclient;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class StompClient {
    private StompSession session;

    @PostConstruct
    public void init() {
//
    }

    public StompSession getSession() {
        return session;
    }

    public void setSession(StompSession session) {
        this.session = session;
    }
}
