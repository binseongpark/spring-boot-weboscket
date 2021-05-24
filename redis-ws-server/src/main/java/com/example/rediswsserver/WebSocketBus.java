package com.example.rediswsserver;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class WebSocketBus implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;

    private String busId;

    public static WebSocketBus create() {
        WebSocketBus webSocketBus = new WebSocketBus();
        webSocketBus.busId = UUID.randomUUID().toString();
        return webSocketBus;
    }
}
