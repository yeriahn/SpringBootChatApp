package com.noti.chatapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ChatMessage {
    private String type;
    private String content;
    @Setter
    private String sender;
    private LocalDateTime dateTime;

    public ChatMessage() {
        dateTime = LocalDateTime.now();
    }

    @Builder
    public ChatMessage(String type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.dateTime = LocalDateTime.now();
    }

    public void enter(String sender) {
        this.sender = sender;
        this.content = this.sender + " 님이 입장하였습니다.";
    }
}
