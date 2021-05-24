package com.example.rediswsserver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class WebSocketRepository {
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private static final String TEST = "TEST";
    private final RedisTemplate<String, Object> redisTemplate;
    // private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private HashOperations<String, String, WebSocketBus> opsHashBus;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로
    // 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashBus = redisTemplate.opsForHash();
        topics = new HashMap<>();

        createBus();
        connectBus("8618dbc9-71a1-4ae6-87e5-ef8131d9afa4");
    }

    public List<WebSocketBus> findAllBus() {
        return opsHashBus.values(TEST);
    }

    public WebSocketBus createBus() {
        WebSocketBus webSocketBus = WebSocketBus.create();
        opsHashBus.put(TEST, webSocketBus.getBusId(), webSocketBus);
        return webSocketBus;
    }

    public void connectBus(String busId) {
        ChannelTopic topic = topics.get(busId);
        if (topic == null)
            topic = new ChannelTopic(busId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(busId, topic);
    }

    public ChannelTopic getTopic(String busId) {
        return topics.get(busId);
    }
}
