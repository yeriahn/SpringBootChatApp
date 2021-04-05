package com.noti.chatapp.config.security;

import com.noti.chatapp.domain.setting.Member;
import com.noti.chatapp.dto.setting.MemberDto;
import com.noti.chatapp.service.security.UserDetailsServiceImpl;
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
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final Cookie jwtToken = cookieUtil.getCookie(httpServletRequest,jwtTokenProvider.ACCESS_TOKEN_NAME);

        log.info("JwtRequestFilter jwtToken: {}",jwtToken);

        String username = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshUname = null;

        try{
            if(jwtToken != null){
                jwt = jwtToken.getValue();
                username = jwtTokenProvider.getUserNameFromJwt(jwt);
                log.info("JwtRequestFilter jwt: {}",jwt);
                log.info("JwtRequestFilter username: {}",username);
            }
            if(username!=null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(jwtTokenProvider.validateToken(jwt,userDetails)){
                    log.info("유효성 통과");
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    log.info("userDetails : {}",userDetails.toString());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    log.info("userDetails : {}",userDetails.toString());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){
            Cookie refreshToken = cookieUtil.getCookie(httpServletRequest,jwtTokenProvider.REFRESH_TOKEN_NAME);
            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }
        }catch(Exception e){

        }

        try{
            if(refreshJwt != null){
                refreshUname = redisUtil.getData(refreshJwt);

                if(refreshUname.equals(jwtTokenProvider.getUserNameFromJwt(refreshJwt))){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUname);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    Member member = new Member();
                    member.setMemberId(refreshUname);
                    String newToken =jwtTokenProvider.generateJwtToken(member);

                    Cookie newAccessToken = cookieUtil.createCookie(jwtTokenProvider.ACCESS_TOKEN_NAME,newToken);
                    httpServletResponse.addCookie(newAccessToken);
                }
            }
        }catch(ExpiredJwtException e){

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
