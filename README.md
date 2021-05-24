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