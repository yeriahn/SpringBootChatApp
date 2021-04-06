package com.noti.chatapp.exception;

public class ParticipantCountException extends RuntimeException{
    private String requestRoomId;

    public ParticipantCountException(String requestRoomId) {
        super();
        this.requestRoomId = requestRoomId;
    }
}
