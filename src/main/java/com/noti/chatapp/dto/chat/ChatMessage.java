package com.noti.chatapp.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@ToString
public class ChatMessage {
    private String type;
    private String content;
    @Setter
    private String sender;
    private String roomId;

    @Builder
    public ChatMessage(String type, String content, String sender,String roomId) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.roomId = roomId;
    }

    public void enter(String sender) {
        this.sender = sender;
        this.content = this.sender + " 님이 입장하였습니다.";
    }
}
