package com.noti.chatapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noti.chatapp.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatRoomDto {

    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public ChatRoomDto(String name) {
        this.name = name;
    }

    public ChatRoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.createdDate = chatRoom.getCreatedDate();
        this.modifiedDate = chatRoom.getModifiedDate();
    }


    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .name(name)
                .build();
    }
}
