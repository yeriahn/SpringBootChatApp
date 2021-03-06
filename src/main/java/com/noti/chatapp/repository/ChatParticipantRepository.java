package com.noti.chatapp.repository;

import com.noti.chatapp.domain.chat.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByRoomId(String roomId);

    void deleteByRoomId(String roomId);

    Integer countByRoomId(String roomId);
}
