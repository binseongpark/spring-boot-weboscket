### 유용한 링크
- http://useof.org/java-open-source/org.springframework.messaging.simp.stomp.StompSession - StompSession sample
- https://github.com/aliakh/demo-spring-websocket - spring-boot websocket demo
- https://github.com/khaledkucse/usaskchat - usaskchat websocket sample github

### todo list
- vue 에다가 sockjs, stompclient 붙이기
- websocket server 만들기


### 시나리오
- back-ws post api /start /error /ping /stop 일단 단순 문자열 리턴으로만 처리?
- post api 들어올때 send 발생
- front-ws 는 client server 두개가 떠있어야함
- subscription 발생 시(ws-client) 다시 열려있는 서버(ws-server)에서 send 발생
- payload encoding 문제 해결하기