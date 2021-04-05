package com.noti.chatapp.config.security;

import com.noti.chatapp.service.handler.CustomLoginSuccessHandler;
import com.noti.chatapp.service.handler.CustomFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Lazy
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    private final CustomFailureHandler customFailureHandler;

    @Override
    // 정적 자원에 대해서는 Security 설정을 적용하지 않음
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다. on으로 설정해도 되나 설정할경우 웹페이지에서 추가처리가 필요함.
                .headers()
                .frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //// 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
            .and()
                .authorizeRequests()
                //.antMatchers("/setting/regMember", "/setting/loginMember", "/setting/loginMember/fail", "/error/*").permitAll()
                //.antMatchers("/h2/**").permitAll()
                //.anyRequest().authenticated() //그 외의 모든 요청은 인증된 사용자만 접근가능
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                .anyRequest().permitAll()
            .and()
                .formLogin() // 권한없이 페이지 접근하면 로그인 페이지로 이동한다.
                .loginPage("/setting/loginMember")
                //.loginProcessingUrl("/loginProc")
                //.usernameParameter("memberId")
                //.passwordParameter("memberPw")
                //.successHandler(customLoginSuccessHandler)
                .successForwardUrl("/chat/room")
                //.failureHandler(customFailureHandler)
                //.failureUrl("/setting/loginMember?error=true").permitAll()
            //.and()
                //.exceptionHandling()
                //.accessDeniedHandler(customAccessDeniedHandler)
            .and()
                .logout()
                .logoutSuccessUrl("/setting/loginMember")
            .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/loginProc");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(customFailureHandler);
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }

}
