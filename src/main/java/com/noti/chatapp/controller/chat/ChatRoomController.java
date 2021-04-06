package com.noti.chatapp.controller.chat;

import com.noti.chatapp.domain.chat.ChatRoom;
import com.noti.chatapp.dto.PageDto;
import com.noti.chatapp.dto.chat.ChatRoomDto;
import com.noti.chatapp.dto.LoginDto;
import com.noti.chatapp.service.ChatRoomService;
import com.noti.chatapp.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public String getCurrentMemberId () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    //채팅 리스트 화면
    @GetMapping("/chat/room")
    public String rooms(Map<String, Object> model) {

        String currentMemberId = getCurrentMemberId();
        model.put("currentMemberId", currentMemberId);

        return "/chat/room";
    }

    //채팅방 입장 화면
    @GetMapping("/chat/room/detail")
    public String roomDetail(Model model) {
        String currentMemberId = getCurrentMemberId();
        model.addAttribute("currentMemberId", currentMemberId);
        return "chat/room_detail";
    }

    //채팅방 id별 입장
    @GetMapping("/chat/room/detail/{roomId}")
    public String chatRoomDetail(Model model, @PathVariable String roomId) {
        ChatRoomDto chatRoomDto = chatRoomService.findByRoomId(roomId);
        chatRoomService.joinCount(roomId);
        String currentMemberId = getCurrentMemberId();

        model.addAttribute("currentMemberId", currentMemberId);
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoomDto", chatRoomDto);

        return "/chat/room_detail";
    }

    /**
     * 채팅방 리스트 호출
     *
     * @param pageable 페이징
     * @param //totalElements 조건에 해당되는 전체 레코드 갯수. 이 값이 전달되면 해당 값을 Page 객체에 삽입 후 Count SQL을 수행하지 않음.
     * @return
     */
    /*@GetMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<Page<ChatRoomDto>> getChatRooms(final Pageable pageable, @RequestParam(required = false, name = "total_elements")Integer totalElements) {
        Page<ChatRoomDto> all = chatRoomService.findAll(pageable, totalElements);
        return ResponseEntity.ok(all);
    }*/


    /*3.28 완료
    @GetMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<Page<ChatRoomDto>> getChatRooms(final Pageable pageable) {
        Page<ChatRoomDto> all = chatRoomService.findAll(pageable);
        return ResponseEntity.ok(all);
    }*/

    @GetMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<PageDto<ChatRoomDto>> getChatRooms(
            @RequestParam(value="category", required=false) String category,
            @RequestParam(value="name", required=false) String name,
            @PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC )Pageable pageable) {
        Page<ChatRoom> ChatRoomList = chatRoomService.findAll(category, name, pageable);
        List<ChatRoomDto> chatRoomDtoList = ChatRoomList.stream().map(ChatRoomDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(PageDto.of(ChatRoomList, chatRoomDtoList));
    }

    @PostMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<ChatRoom> save(@Valid @RequestBody ChatRoomDto requestDto) {
        // TODO: Validation 처리
        String currentMemberId = getCurrentMemberId();

        requestDto.setCreateName(currentMemberId);
        ChatRoom save = chatRoomService.save(requestDto);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping(value = "/api/chat/delete-room")
    public @ResponseBody
    ResponseEntity<Integer> chatRoomDelete(@Valid @RequestBody ChatRoomDto requestDto) {
        int result = chatRoomService.deleteByRoomIdAndRoomPw(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/chat/user")
    @ResponseBody
    public LoginDto getUserInfo() {
        String name = getCurrentMemberId();
        return LoginDto.builder().name(name).build();
    }

    //채팅방 입장 화면
    @GetMapping("/error/room_failed")
    public String roomFailed() {
        return "/error/room_failed";
    }

    //채팅방 입장 화면
    @GetMapping("/error/participant_join_failed")
    public String participantJoinFailed() {
        return "/error/participant_join_failed";
    }
}
