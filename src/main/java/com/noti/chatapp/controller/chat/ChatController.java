package com.noti.chatapp.controller.chat;

import com.noti.chatapp.dto.ChatMessage;
import com.noti.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    /**
     * 메시지 전송
     *
     * @param chatMessage
     * @return
     */
    @MessageMapping("/{roomId}/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
        log.info("send message " + chatMessage.getRoomId() + " from" + principal.getName());
        chatMessage.setSender(principal.getName());

        chatService.sendChatMessage(chatMessage);
    }

    /**
     * 새로운 유저 입장
     *
     * @param chatMessage
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/{roomId}/chat.newUser")
    public void addUser(@Payload ChatMessage chatMessage, Principal principal
            , SimpMessageHeaderAccessor headerAccessor) {
        log.info("chat.newUser roomId : {} ", chatMessage.getRoomId());
        log.info("chat.newUser memberId : {} ", principal.getName());
        String sender = principal.getName();

        chatMessage.enter(sender);

        headerAccessor.getSessionAttributes().put("sender", sender);
        headerAccessor.getSessionAttributes().put("roomId", chatMessage.getRoomId());

        log.info("sender : {}", sender);
        log.info("channelTopic.getContent() : {}", chatMessage.getContent());

        chatService.sendChatMessage(chatMessage);
    }

}
