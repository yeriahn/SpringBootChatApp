package com.noti.chatapp.controller.setting;

import com.noti.chatapp.domain.Member;
import com.noti.chatapp.repository.MemberRepository;
import com.noti.chatapp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/setting")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/memberList")
    public String memberList(@AuthenticationPrincipal User user, Map<String, Object> model) {
        //List<Member> members = memberRepository.findAll();
        //model.put("members", members);
        model.put("currentMemberId", user.getUsername()); //로그인을 통해 인증된 유저 정보 저장
        return "setting/member_list";
    }

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
