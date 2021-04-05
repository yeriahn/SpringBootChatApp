package com.noti.chatapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(jwtTokenInterceptor) //핸들러를 지정
                .addPathPatterns("/**") //인터셉트할 기본 패턴을 지정
                .excludePathPatterns("/setting/regMember", "/setting/loginMember", "/error/**", "/img/**", "/js/**", "/css/**");
    }*/
}
