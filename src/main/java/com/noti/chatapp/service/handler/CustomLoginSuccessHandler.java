package com.noti.chatapp.service.handler;

import com.noti.chatapp.config.security.JwtTokenProvider;
import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.MemberDetailsDto;
import com.noti.chatapp.dto.setting.MemberDto;
import com.noti.chatapp.util.CookieUtil;
import com.noti.chatapp.util.RedisUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = ((MemberDetailsDto)authentication.getPrincipal()).getMember();

        String token = jwtTokenProvider.generateJwtToken(member);
        String refreshJwt = jwtTokenProvider.generateRefreshJwtToken(member);

        Cookie accessToken = cookieUtil.createCookie(jwtTokenProvider.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(jwtTokenProvider.REFRESH_TOKEN_NAME, refreshJwt);

        redisUtil.setDataExpire(refreshJwt, member.getMemberId(), jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        response.sendRedirect("/chat/room");


    }
}
