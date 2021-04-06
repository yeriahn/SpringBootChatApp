package com.noti.chatapp.controller.setting;

import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.setting.MemberDto;
import com.noti.chatapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/setting")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/regMember")
    public String regMemberForm(Principal principal, MemberDto memberdto) {
        if(principal != null) {
            return "redirect:/chat/room";
        }
        return "setting/member_reg";
    }

    @ResponseBody
    @PostMapping("/regMember")
    public Map<String, Object> regMember(@Valid @RequestBody MemberDto memberdto) {
        Map<String, Object> response = new HashMap<>();

        try{
            memberService.regMember(memberdto);
            response.put("success", true);
            return response;
        }
        catch(Exception e){
            response.put("success", false);
        }

        return response;
    }

    @GetMapping("/loginMember")
    public String loginMemberForm(Principal principal) {
        if(principal != null) {
            return "redirect:/chat/room";
        }
        return "setting/member_login";
    }

    @PostMapping("/loginMember/fail")
    public String loginMemberForm(HttpServletRequest request, String loginFailMsg) {
        return "setting/member_login";
    }
}
