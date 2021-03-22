package com.noti.chatapp.controller.chat;

import com.noti.chatapp.domain.chat.ChatRoom;
import com.noti.chatapp.domain.Member;
import com.noti.chatapp.dto.ChatRoomDto;
import com.noti.chatapp.repository.MemberRepository;
import com.noti.chatapp.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final MemberRepository memberRepository;
    private final ChatRoomService chatRoomService;

    //채팅 리스트 화면
    @GetMapping("/chat/room")
    public String rooms(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        model.put("currentMemberId", user.getUsername()); //로그인을 통해 인증된 유저 정보 저장

        return "/chat/room";
    }

    //채팅방 입장 화면
    @GetMapping("/chat/room/detail")
    public String roomDetail(Model model) {
        return "chat/room_detail";
    }

    //채팅방 id별 입장
    @GetMapping("/chat/room/detail/{roomId}")
    public String chatRoomDetail(Model model, @PathVariable Long roomId) {
        log.info("roomId :"+roomId);
        chatRoomService.findById(roomId);
        model.addAttribute("roomId", roomId);
        return "/chat/room_detail";
    }

    @GetMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<List<ChatRoomDto>> getChatRooms() {
        List<ChatRoomDto> all = chatRoomService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<ChatRoom> save(@RequestBody ChatRoomDto requestDto) {
        // TODO: Validation 처리
        ChatRoom save = chatRoomService.save(requestDto);
        return ResponseEntity.ok(save);
    }


}
