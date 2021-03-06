package com.noti.chatapp.controller.chat;

import com.noti.chatapp.domain.chat.ChatParticipant;
import com.noti.chatapp.dto.chat.ChatMessage;
import com.noti.chatapp.dto.chat.ChatParticipantDto;
import com.noti.chatapp.service.ChatParticipantService;
import com.noti.chatapp.service.ChatService;
import com.noti.chatapp.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

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
    public void callParticipants(@DestinationVariable String roomId) {

        List<ChatParticipantDto> chatParticipantDtos = chatParticipantService.findByRoomId(roomId);

        simpleMessageSendingOperation.convertAndSend("/topic/chatting." + roomId, chatParticipantDtos);
    }

    /**
     * 메시지 전송
     *
     * @param chatMessage
     * @return
     */
    @MessageMapping("/{roomId}/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, @AuthenticationPrincipal Principal principal) {

        String sender = principal.getName();

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
    public void addUser(@Payload ChatMessage chatMessage
            , SimpMessageHeaderAccessor headerAccessor, @AuthenticationPrincipal Principal principal) {

        String sender = principal.getName();

        chatMessage.enter(sender);

        ChatParticipant savedParticipant = chatParticipantService.saveParticipant(chatMessage, sender);

        headerAccessor.getSessionAttributes().put("participantId", savedParticipant.getId());
        headerAccessor.getSessionAttributes().put("sender", sender);
        headerAccessor.getSessionAttributes().put("roomId", chatMessage.getRoomId());

        chatService.sendChatMessage(chatMessage);
    }

}
