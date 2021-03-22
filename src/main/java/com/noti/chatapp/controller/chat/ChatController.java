package com.noti.chatapp.controller.chat;

import com.noti.chatapp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations simpleMessageSendingOperation;

    /**
     * 메시지 전송
     *
     * @param chatMessage
     * @return
     */
    @MessageMapping("/{roomId}/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable Long roomId) {
        log.info("send message " + roomId + " from" +chatMessage.getSender());
        chatMessage.setSender(chatMessage.getSender());

        simpleMessageSendingOperation.convertAndSend("/topic/chatting." + roomId, chatMessage);
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
            , SimpMessageHeaderAccessor headerAccessor
            , @DestinationVariable Long roomId) {
        log.info("chat.newUser roomId : {} ", roomId);
        log.info("chat.newUser memberId : {} ", principal.getName());
        String sender = principal.getName();

        chatMessage.enter(sender);

        headerAccessor.getSessionAttributes().put("sender", sender);
        headerAccessor.getSessionAttributes().put("roomId", roomId);

        simpleMessageSendingOperation.convertAndSend("/topic/chatting." + roomId, chatMessage);
    }

}
