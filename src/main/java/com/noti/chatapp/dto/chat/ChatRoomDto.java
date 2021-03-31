package com.noti.chatapp.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.noti.chatapp.domain.chat.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class ChatRoomDto {

    private Long id;
    private String roomId;
    private String roomPw;
    private String name;
    private String category;
    @Setter
    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public ChatRoomDto(String roomId, String name, String roomPw, String category, String createName) {
        this.roomId = roomId;
        this.roomPw = roomPw;
        this.name = name;
        this.category = category;
        this.createName = createName;
    }

    public ChatRoomDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getRoomId();
        this.name = chatRoom.getName();
        this.roomPw = chatRoom.getRoomPw();
        this.category = chatRoom.getCategory();
        this.createName = chatRoom.getCreateName();
        this.createdDate = chatRoom.getCreatedDate();
        this.modifiedDate = chatRoom.getModifiedDate();
    }


    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(name)
                .roomPw(roomPw)
                .category(category)
                .createName(createName)
                .build();
    }
}
