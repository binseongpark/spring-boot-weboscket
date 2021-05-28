### 유용한 링크

- http://useof.org/java-open-source/org.springframework.messaging.simp.stomp.StompSession - StompSession sample
- https://github.com/aliakh/demo-spring-websocket - spring-boot websocket demo
- https://github.com/khaledkucse/usaskchat - usaskchat websocket sample github
- https://velog.io/@koseungbin/WebSocket - weboscket concept blog(정독해보기)
- https://handcoding.tistory.com/171
- https://daddyprogrammer.org/post/4731/spring-websocket-chatting-server-redis-pub-sub/
- https://kouzie.github.io/spring/Spring-Boot-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-WebSocket/#
- https://github.com/codej99/websocket-chat-server
- https://github.com/codej99/websocket-chat-server/tree/feature/redis-pub-sub/src/main/java/com/websocket/chat/controller
  - redis pub sub 구조

### todo list

- springboot websocket client -> server 로 보내는 api 만들기
- vuejs 에서 다양한 시나리오. 화면을 보고있는 사람 표시. 작업중이면 버튼 막기
- springboot client reconnect 방안 
- 다른 messagequeue 예제 찾아보기(rabbitmq, kafka)

### 시나리오

- back-ws post api /start /error /ping /stop 일단 단순 문자열 리턴으로만 처리?
- post api 들어올때 send 발생
- front-ws 는 client server 두개가 떠있어야함
- subscription 발생 시(ws-client) 다시 열려있는 서버(ws-server)에서 send 발생
- payload encoding 문제 해결하기

### concept

- sockjs: WebSocket 과 유사한 객체를 제공하는 브라우저 JavaScript 라이브러리. websocket 을 연결할 수 없는 경우 다른 http 기반의 기술로 연결을 시도하는 방법을 지원.
- https://github.com/sockjs/sockjs-client#supported-transports-by-browser-html-served-from-http-or-https - 브라우저 지원 범위
- STOMP: Simple Text Oriented Messaging Protocol 의 약자. TCP 또는 WebSocket 같은 양방향 네트워크 프로토콜 기반으로 동작


import javax.annotation.PostConstruct;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

@Component
public class StompClient {
    private StompSession session;

    @PostConstruct
    public void init() {
        //
    }

    public StompSession getSession() {
        System.out.println("@@@@ getSession: " + session);
        return session;
    }

    public void setSession(StompSession session) {
        this.session = session;
    }
}


. . .
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


@Component
public class WsClient {

    @Value("${spring.websocket.server}")
    private String websocketServer;

    @Autowired
    StompClient stompClient;

    @PostConstruct
    public void init() {
        connect();
    }

    public void connect() {
        System.out.println("connect");

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient client = new WebSocketStompClient(sockJsClient);
        client.setMessageConverter(new StringMessageConverter());

        StompSession session = null;

        try {
            session = client.connect("http://" + websocketServer + "/ws", new WsClientHandler()).get();
        } catch (Exception e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } finally {
                connect();
            }
        }
        System.out.println("connect success");
        System.out.println(session);

        if (session != null) {
            stompClient.setSession(session);
        }
    }

    private class WsClientHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            super.afterConnected(session, connectedHeaders);

            System.out.println("web socket client connect");
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return super.getPayloadType(headers);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
                Throwable exception) {
            super.handleException(session, command, headers, payload, exception);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            super.handleFrame(headers, payload);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            super.handleTransportError(session, exception);

            System.out.println("@@@@ raise handleTransportError");
            if (exception instanceof ConnectionLostException) {
                System.out.println("handleTransportError connect");
                if (session.isConnected()) {
                    session.disconnect();
                }

                connect();
            }
        }

    }
}
