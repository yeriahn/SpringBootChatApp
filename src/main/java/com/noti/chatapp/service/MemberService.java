package com.noti.chatapp.service;

import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.setting.MemberDto;
import com.noti.chatapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true) // memberService 전체 트랜젝션을 읽기 전용으로 해서 성능을 향상
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional //@Transaction으로 옵션을 디폴트로 해서 읽기 쓰기가 가능
    public void regMember(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getMemberId());
        //PasswordEncoder로 비밀번호 암호화

        log.info("???");
        memberRepository.save(Member.builder()
                                .memberId(memberDto.getMemberId())
                                .memberPw(passwordEncoder.encode(memberDto.getMemberPw()))
                                .build());
    }

    private void validateDuplicateMember(String memberId){
        Optional<Member> findMembers = memberRepository.findByMemberId(memberId);

        log.info("findMembers1 :"+findMembers);
        findMembers.ifPresent(findMember -> {
            throw new IllegalStateException("이미 존재하는 회원");
        });
        log.info("findMembers2 :"+findMembers);
    }
}
