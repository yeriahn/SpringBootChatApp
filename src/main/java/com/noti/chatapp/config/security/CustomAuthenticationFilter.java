package com.noti.chatapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.setting.MemberDto;
import com.noti.chatapp.exception.InputNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //UsernamePasswordAuthenticationToken authRequest;
        //Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
        //authRequest = new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getMemberPw());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("memberId"), request.getParameter("memberPw"));
        log.info("CustomAuthenticationFilter {},",authRequest.toString());
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
