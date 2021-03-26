package com.noti.chatapp.controller.chat;

import com.noti.chatapp.domain.chat.ChatParticipant;
import com.noti.chatapp.dto.chat.ChatMessage;
import com.noti.chatapp.dto.chat.ChatParticipantDto;
import com.noti.chatapp.service.ChatParticipantService;
import com.noti.chatapp.service.ChatService;
import com.noti.chatapp.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatService chatService;
    private final ChatParticipantService chatParticipantService;

    private final SimpMessageSendingOperations simpleMessageSendingOperation;

    /**
     * 대화 참여자 목록 호출
     *
     * @param
     * @return
     */
    @MessageMapping("/{roomId}/chat.callParticipants")
    public void callParticipants(@DestinationVariable String roomId, @Header("token") String token) {

        String sender = jwtTokenProvider.getUserNameFromJwt(token);

        List<ChatParticipantDto> chatParticipantDtos = chatParticipantService.findByRoomId(roomId);

        log.info("chatParticipantDtos :"+chatParticipantDtos.toString());

        simpleMessageSendingOperation.convertAndSend("/topic/chatting." + roomId, chatParticipantDtos);
    }

    /**
     * 메시지 전송
     *
     * @param chatMessage
     * @return
     */
    @MessageMapping("/{roomId}/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, @Header("token") String token) {

        String sender = jwtTokenProvider.getUserNameFromJwt(token);

        log.info("send message " + chatMessage.getRoomId() + " from" + sender);
        chatMessage.setSender(sender);

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
    public void addUser(@Payload ChatMessage chatMessage, @Header("token") String token
            , SimpMessageHeaderAccessor headerAccessor) {

        String sender = jwtTokenProvider.getUserNameFromJwt(token);

        log.info("chat.newUser roomId : {} ", chatMessage.getRoomId());
        log.info("chat.newUser memberId : {} ", sender);

        chatMessage.enter(sender);

        ChatParticipant savedParticipant = chatParticipantService.saveParticipant(chatMessage, sender);

        headerAccessor.getSessionAttributes().put("participantId", savedParticipant.getId());
        headerAccessor.getSessionAttributes().put("sender", sender);
        headerAccessor.getSessionAttributes().put("roomId", chatMessage.getRoomId());

        log.info("participantId : {}", savedParticipant.getId());
        log.info("sender : {}", sender);
        log.info("channelTopic.getContent() : {}", chatMessage.getContent());

        chatService.sendChatMessage(chatMessage);
    }

}
