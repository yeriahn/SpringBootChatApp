package com.noti.chatapp.service.handler;

import com.noti.chatapp.dto.ChatMessage;
import com.noti.chatapp.service.ChatService;
import com.noti.chatapp.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatService chatService;

    private final JwtTokenProvider jwtTokenProvider;

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

                    String jwtToken = accessor.getFirstNativeHeader("token");
                    log.info("CONNECT jwtToken {}", jwtToken);
                    //Header의 jwt token 검증
                    jwtTokenProvider.validateToken(jwtToken);
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

                        chatService.sendChatMessage(chatMessage);
                    }

                    break;
                default:
                    break;
            }
        }
        return message;
    }


}
