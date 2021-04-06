package com.noti.chatapp.config.security;

import com.noti.chatapp.dto.MemberDetailsDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;
    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String memberId = token.getName();
        String memberPw = (String) token.getCredentials();
        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        MemberDetailsDto memberDetailsDto = (MemberDetailsDto) userDetailsService.loadUserByUsername(memberId);
        if (!passwordEncoder.matches(memberPw, memberDetailsDto.getPassword())) {
            throw new BadCredentialsException(memberDetailsDto.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(memberDetailsDto, memberPw, memberDetailsDto.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
