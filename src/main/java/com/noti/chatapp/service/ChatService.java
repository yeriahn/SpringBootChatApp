package com.noti.chatapp.service;

import com.noti.chatapp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {
        log.info("service - topic : {}",channelTopic.getTopic());
        log.info("service - chatMessage : {}", chatMessage.toString());
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
