package com.noti.chatapp.config.security;

import com.noti.chatapp.service.handler.CustomLoginSuccessHandler;
import com.noti.chatapp.service.handler.CustomFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    private final CustomFailureHandler customFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다. on으로 설정해도 되나 설정할경우 웹페이지에서 추가처리가 필요함.
                .headers()
                .frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
            .and()
                .authorizeRequests()
                .antMatchers("/setting/regMember", "/setting/loginMember", "/setting/loginMember/fail").permitAll()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated() //그 외의 모든 요청은 인증된 사용자만 접근가능
            .and()
                .formLogin() // 권한없이 페이지 접근하면 로그인 페이지로 이동한다.
                .loginPage("/setting/loginMember")
                .loginProcessingUrl("/loginProc")
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customFailureHandler)
                //.failureUrl("/setting/loginMember?error=true").permitAll()
            //.and()
                //.exceptionHandling()
                //.accessDeniedHandler(customAccessDeniedHandler)
            .and()
                .logout()
                .logoutSuccessUrl("/setting/loginMember");
    }

}
