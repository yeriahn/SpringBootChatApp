package com.noti.chatapp.config;

import com.noti.chatapp.config.security.JwtTokenProvider;
import com.noti.chatapp.util.CookieUtil;
import com.noti.chatapp.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final Cookie jwtToken = cookieUtil.getCookie(request,jwtTokenProvider.ACCESS_TOKEN_NAME);

        String username = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUname = null;

        try{
            if(jwtToken != null){
                jwt = jwtToken.getValue();
                log.info("JwtTokenInterceptor jwt : {}",jwt);
            }
        }catch (ExpiredJwtException e){
            //Access Token이 유효하지 않으면 Refresh Token값을 읽어들임
            Cookie refreshToken = cookieUtil.getCookie(request,jwtTokenProvider.REFRESH_TOKEN_NAME);
            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }
        }catch(Exception e){

        }

        response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", jwt);
        response.setHeader("REFRESH_TOKEN", refreshJwt);
        response.setHeader("msg", "Check the tokens.");

        return true;
    }
}
