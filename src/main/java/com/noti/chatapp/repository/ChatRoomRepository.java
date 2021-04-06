package com.noti.chatapp.repository;

import com.noti.chatapp.domain.chat.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    //Page<ChatRoom> findAll(Pageable pageable, Integer totalElements);

    Page<ChatRoom> findByCategoryContainingAndNameContaining(String category, String name, Pageable pageable);

    Optional<ChatRoom> findByRoomId(String roomId);

    Integer deleteByRoomIdAndRoomPw(String roomId, String roomPw);
}
