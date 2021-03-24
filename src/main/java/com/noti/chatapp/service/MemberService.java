package com.noti.chatapp.service;

import com.noti.chatapp.domain.Member;
import com.noti.chatapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void regMember(Member member) {
        //PasswordEncoder로 비밀번호 암호화
        member.setUserPw(passwordEncoder.encode(member.getMemberPw()));
        memberRepository.save(member);
    }
}
