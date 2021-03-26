package com.noti.chatapp.domain.chat;

import com.noti.chatapp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class ChatParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String memberId;

    @Builder
    public ChatParticipant(String roomId, String memberId) {
        this.roomId = roomId;
        this.memberId = memberId;
    }
}