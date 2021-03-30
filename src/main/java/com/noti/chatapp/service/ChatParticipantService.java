package com.noti.chatapp.service;

import com.noti.chatapp.domain.chat.ChatParticipant;
import com.noti.chatapp.dto.chat.ChatMessage;
import com.noti.chatapp.dto.chat.ChatParticipantDto;
import com.noti.chatapp.repository.ChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatParticipantService {

    private final ChatParticipantRepository chatParticipantRepository;

    @Transactional(rollbackFor = Exception.class)
    public ChatParticipant saveParticipant(ChatMessage chatMessage, String sender) {

        ChatParticipantDto chatParticipantDto = ChatParticipantDto.builder()
                .roomId(chatMessage.getRoomId())
                .memberId(sender)
                .build();

        return chatParticipantRepository.save(chatParticipantDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<ChatParticipantDto> findByRoomId(String roomId) {

        return chatParticipantRepository.findByRoomId(roomId)
                .stream().map(ChatParticipantDto::new).collect(Collectors.toList());
    }
}
