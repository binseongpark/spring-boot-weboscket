package com.example.rediswsserver;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private final WebSocketRepository webSocketRepository;

    private final RedisPublisher redisPublisher;

    // private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/ping")
    @ResponseBody
    public void ping(String busId) {
        logger.info("@@@@ busId: " + busId);
        // messagingTemplate.convertAndSend("/recv", "😘");
        // redisPublisher.publish(webSocketRepository.getTopic(message.getRoomId()),
        // message);
        // redisPublisher.publish(webSocketRepository.getTopic(message.getBusId()),
        // message.getMessage());
        redisPublisher.publish(webSocketRepository.getTopic(busId), "😘");
        // return "😘";
    }

    @SubscribeMapping("/subscribe")
    public String sendOneTimeMessage() {
        logger.info("Subscription via the application");
        return "server one-time message via the application";
    }

    @GetMapping("/buses")
    @ResponseBody
    public List<WebSocketBus> bus() {
        return webSocketRepository.findAllBus();
    }
}
