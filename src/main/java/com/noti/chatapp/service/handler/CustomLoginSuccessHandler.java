package com.noti.chatapp.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // IP, 세션 ID
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        log.info("IP : " + web.getRemoteAddress());
        log.info("Session ID : " + web.getSessionId());

        // 인증 ID
        log.info("name : " + authentication.getName());

        // 권한 리스트
        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
        log.info("권한 : ");
        for(int i = 0; i< authList.size(); i++) {
            log.info(authList.get(i).getAuthority() + " ");
        }

        // 디폴트 URI
        String uri = "/chat/room";

        /*
        // 접근 권한이 없는 페이지 요청 → 로그인 화면(인터셉트)의 경우 데이터 get
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 로그인 버튼 눌러 접속했을 경우의 데이터 get
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }

        // null이 아니라면 강제 인터셉트 당했다는 것
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();

            // ""가 아니라면 직접 로그인 페이지로 접속한 것
        } else if (prevPage != null && !prevPage.equals("")) {
            uri = prevPage;
        }
        */

        // 세 가지 케이스에 따른 URI 주소로 리다이렉트
        response.sendRedirect(uri);
    }
}
