package com.noti.chatapp.domain.chat;

import com.noti.chatapp.domain.BaseTimeEntity;
import com.noti.chatapp.util.CryptoConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;

    @Convert(converter = CryptoConverter.class)
    private String roomPw;
    private String name;
    private String category;

    @Builder
    public ChatRoom(String roomId, String name, String roomPw, String category) {
        this.roomId = roomId;
        this.roomPw = roomPw;
        this.name = name;
        this.category = category;
    }

/*    public static ChatRoom create(String name, String category) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.category = category;
        return chatRoom;
    }*/
}
