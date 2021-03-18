package com.noti.chatapp.controller.chat;

import com.noti.chatapp.domain.Member;
import com.noti.chatapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final MemberRepository memberRepository;

    //채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        model.put("currentMemberId", user.getUsername()); //로그인을 통해 인증된 유저 정보 저장

        return "chat/room";
    }

    //채팅방 입장 화면
    @GetMapping("/room/detail")
    public String roomDetail(Model model) {
        return "chat/room_detail";
    }


}
