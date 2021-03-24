package com.noti.chatapp.config.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.noti.chatapp.dto.ChatMessage;
import com.noti.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final RedisTemplate redisTemplate;
    private final ChatService chatService;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel)  {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("accessor : {}",accessor);
        String sender = (String) accessor.getSessionAttributes().get("sender");
        String roomId = (String) accessor.getSessionAttributes().get("roomId");
        log.info("handler - sender : {}", sender);
        log.info("handler - roomId : {}", roomId);

        log.info("accessor.getCommand() : {}", accessor.getCommand());
        if (accessor.getCommand() != null) {

            switch (accessor.getCommand()) {
                case CONNECT:
                    // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                    log.debug("Login Id : {}", accessor.getLogin());
                    log.debug("User Token : {}", accessor.getPasscode());
                    break;
                case CONNECTED:
                    break;
                case SUBSCRIBE:
                    log.debug("SUBSCRIBE target:{}", message.toString());
                    break;
                case SEND:
                    break;
                case ERROR:
                    log.error("SOCKET ERROR userId : {}",accessor.getLogin());
                    break;
                case DISCONNECT:
                    log.debug("DISCONNECT SOCKET");
                    //퇴장 이벤트
                    if (Objects.nonNull(sender)) {
                        ChatMessage chatMessage = ChatMessage.builder()
                                .roomId(roomId)
                                .type("Leave")
                                .sender(sender)
                                .content(sender + " 님이 퇴장하였습니다.").build();
                        log.info("11?? sender :" + sender);
                        log.info("11?? roomId :" + roomId);
                        chatService.sendChatMessage(chatMessage);
                        //redisTemplate.convertAndSend("/topic/chatting." + roomId, chatMessage);
                        log.info("22??");
                    }

                    break;
                default:
                    break;
            }
        }
        return message;
    }


}
