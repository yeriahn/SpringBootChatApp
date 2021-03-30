package com.noti.chatapp.exception;

import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends RuntimeException {
    private String requestRoomId;

    public ChatRoomNotFoundException(String requestRoomId) {
        super();
        this.requestRoomId = requestRoomId;
    }
}
