package com.noti.chatapp.service;

import com.noti.chatapp.domain.ChatRoom;
import com.noti.chatapp.dto.ChatRoomDto;
import com.noti.chatapp.exception.ChatRoomNotFoundException;
import com.noti.chatapp.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomDto findById(Long id) {
        ChatRoom entity = chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException(id));
        return new ChatRoomDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAll() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomDto::new) // posts -> new PostsListResponseDto(posts)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom save(ChatRoomDto requestDto) {
        ChatRoom chatRoom = requestDto.toEntity();
        return chatRoomRepository.save(chatRoom);
    }
}
