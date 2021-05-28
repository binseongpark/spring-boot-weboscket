package com.example.rediswsclient;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocketMessage implements Serializable {
    /**
     * Statements
     * (long)serialVersionUID 
     */
    private static final long serialVersionUID = 486640239514383443L;
    private String topic;
    private String message;
}
