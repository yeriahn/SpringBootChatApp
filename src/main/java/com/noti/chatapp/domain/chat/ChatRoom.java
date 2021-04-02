package com.noti.chatapp.domain.chat;

import com.noti.chatapp.domain.BaseTimeEntity;
import com.noti.chatapp.util.CryptoConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String roomId;

    @NotNull
    @Convert(converter = CryptoConverter.class)
    private String roomPw;

    @NotNull
    private String name;

    private String category;

    private String createName;

    @Builder
    public ChatRoom(String roomId, String name, String roomPw, String category, String createName) {
        this.roomId = roomId;
        this.roomPw = roomPw;
        this.name = name;
        this.category = category;
        this.createName = createName;
    }

/*    public static ChatRoom create(String name, String category) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.category = category;
        return chatRoom;
    }*/
}
