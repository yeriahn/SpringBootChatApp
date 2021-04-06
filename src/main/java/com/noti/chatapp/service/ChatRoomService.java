package com.noti.chatapp.service;

import com.noti.chatapp.domain.chat.ChatRoom;
import com.noti.chatapp.dto.chat.ChatRoomDto;
import com.noti.chatapp.exception.ChatRoomNotFoundException;
import com.noti.chatapp.exception.ParticipantCountException;
import com.noti.chatapp.repository.ChatRoomRepository;
import com.noti.chatapp.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantService chatParticipantService;

    @Transactional(readOnly = true)
    public ChatRoomDto findByRoomId(String roomId) {
        ChatRoom entity = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(roomId));
        return new ChatRoomDto(entity);
    }

    /*@Transactional(readOnly = true)
    public Page<ChatRoomDto> findAll(Pageable pageable, Integer totalElements) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findAll(pageable, totalElements);
        return chatRooms.map(ChatRoomDto::new);
    }*/

    /*3.28 완료
    @Transactional(readOnly = true)
    public Page<ChatRoomDto> findAll(Pageable pageable) {
        return chatRoomRepository.findAll(pageable)
                //.stream()
                .map(ChatRoomDto::new); // posts -> new PostsListResponseDto(posts)
                //.collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public Page<ChatRoom> findAll(String category, String name, Pageable pageable) {
        return chatRoomRepository.findByCategoryContainingAndNameContaining(category, name, PageUtil.convertToZeroBasePageWithSort(pageable));
    }

    @Transactional(rollbackFor = Exception.class)
    public ChatRoom save(ChatRoomDto requestDto) {
        ChatRoom chatRoom = requestDto.toEntity();
        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByRoomIdAndRoomPw(ChatRoomDto requestDto) {

        int result = chatRoomRepository.deleteByRoomIdAndRoomPw(requestDto.getRoomId(), requestDto.getRoomPw());

        if(result == 1) {
            chatParticipantService.deleteByRoomId(requestDto.getRoomId());
        }

        return result;
    }

    @Transactional(readOnly = true)
    public void joinCount(String roomId) {

        int participantCount = chatParticipantService.countByRoomId(roomId);

        if(participantCount >= 10) {
            throw new ParticipantCountException(roomId);
        }
    }

    
}
