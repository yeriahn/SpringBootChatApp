package com.noti.chatapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String name;
    private String token;

    @Builder
    public LoginDto(String name, String token) {
        this.name = name;
        this.token = token;
    }
}
