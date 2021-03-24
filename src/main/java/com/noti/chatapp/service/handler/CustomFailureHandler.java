package com.noti.chatapp.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String loginFailMsg = exception.getMessage();

        if (exception instanceof AuthenticationServiceException) {
            loginFailMsg = "존재하지 않는 사용자입니다.";
        } else if(exception instanceof BadCredentialsException) {
            loginFailMsg = "아이디 혹은 비밀번호가 맞지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            loginFailMsg = "내부적으로 발생한 시스템 문제로 인해 인증 요청을 처리할 수 없습니다.";
        }

        log.info("loginFailMsg :"+loginFailMsg);
        request.setAttribute("loginFailMsg", loginFailMsg);

        // 로그인 페이지로 다시 포워딩
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/setting/loginMember?error=true");
        //dispatcher.forward(request, response);
        request.getRequestDispatcher("/setting/loginMember/fail").forward(request, response);
    }
}
