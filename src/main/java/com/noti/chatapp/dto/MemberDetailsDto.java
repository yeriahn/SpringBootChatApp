package com.noti.chatapp.dto;

import com.noti.chatapp.domain.setting.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
@ToString
public class MemberDetailsDto implements UserDetails {

    @Delegate
    private Member member;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    //계정이 만료되었는지 리턴(true = 만료되지 않음)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //계정이 잠겨있지 않은지 리턴(true = 잠겨있지 않음)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //계정의 패스워드가 만료되지 않았는지 리턴(true = 사용가능)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //계정이 사용가능한지 리턴(true = 사용가능)
    public boolean isEnabled() {
        return true;
    }
}
