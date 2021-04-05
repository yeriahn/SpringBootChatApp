package com.noti.chatapp.service.security;

import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.domain.setting.Role;
import com.noti.chatapp.dto.MemberDetailsDto;
import com.noti.chatapp.exception.UserNotFoundException;
import com.noti.chatapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    /*@Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(memberId));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (memberId.equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getMemberId(), member.getMemberPw(), grantedAuthorities);
    }*/

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        log.info("UserDetailsServiceImpl memberId : {}",memberId);
        return memberRepository.findByMemberId(memberId).map(u -> new MemberDetailsDto(u, Collections.singleton(new SimpleGrantedAuthority(Role.MEMBER.getValue())))).orElseThrow(() -> new UsernameNotFoundException(memberId));
    }
}
