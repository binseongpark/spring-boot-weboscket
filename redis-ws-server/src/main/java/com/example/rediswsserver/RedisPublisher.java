package com.example.rediswsserver;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);
    private final RedisTemplate<String, Object> redisTemplate;

    //    public void publish(ChannelTopic topic, ChatMessage message) {
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }
    public void publish(ChannelTopic topic, String message) {
        logger.info("@@@@ publish: " + topic);
        logger.info("@@@@ publish: " + message);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
