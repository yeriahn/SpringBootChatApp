package com.noti.chatapp.dto.chat;

import com.noti.chatapp.domain.chat.ChatParticipant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatParticipantDto {
    private String roomId;
    private String memberId;

    @Builder
    public ChatParticipantDto(String roomId, String memberId) {
        this.roomId = roomId;
        this.memberId = memberId;
    }

    public ChatParticipantDto(ChatParticipant chatParticipant) {
        this.roomId = chatParticipant.getRoomId();
        this.memberId = chatParticipant.getMemberId();
    }

    public ChatParticipant toEntity() {
        return ChatParticipant.builder()
                .roomId(roomId)
                .memberId(memberId)
                .build();
    }
}