package com.noti.chatapp.service.handler;

import com.noti.chatapp.config.security.JwtTokenProvider;
import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.MemberDetailsDto;
import com.noti.chatapp.dto.setting.MemberDto;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomLoginSuccessHandler ====");
        Member member = ((MemberDetailsDto)authentication.getPrincipal()).getMember();
        String token = jwtTokenProvider.generateJwtToken(member);
        log.info("CustomLoginSuccessHandler token : {}",token);
        response.addHeader("Authorization", "Bearer " + token);
        response.sendRedirect("/chat/room");
        //request.getRequestDispatcher("/chat/room").forward(request, response);


    }
}
