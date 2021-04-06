package com.noti.chatapp.service.handler;

import com.noti.chatapp.config.security.JwtTokenProvider;
import com.noti.chatapp.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final CookieUtil cookieUtil;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        cookieUtil.deleteCookie(request,response,jwtTokenProvider.ACCESS_TOKEN_NAME);
        cookieUtil.deleteCookie(request,response,jwtTokenProvider.REFRESH_TOKEN_NAME);
    }
}
