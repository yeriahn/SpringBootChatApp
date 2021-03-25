package com.noti.chatapp.controller.setting;

import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/setting")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/regMember")
    public String regMemberForm(Member member) {
        return "setting/member_reg";
    }

    @PostMapping("/regMember")
    public String regMember(Member member) {
        //PasswordEncoder로 비밀번호 암호화
        memberService.regMember(member);
        return "redirect:/chat/room";
    }

    @GetMapping("/loginMember")
    public String loginMemberForm() {
        return "setting/member_login";
    }

    @PostMapping("/loginMember/fail")
    public String loginMemberForm(HttpServletRequest request, String loginFailMsg) {
        log.info("loginFailMsg2 : loginFailMsg");
        return "setting/member_login";
    }

    /*
    @RequestMapping(value = "/loginMember", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginMemberForm() {
        return "setting/member_login";
    }

     */
}
