package com.noti.chatapp.dto.setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberDto {

    @NotBlank
    private String memberId;

    @Setter
    @NotBlank
    private String memberPw;

    @Builder
    public MemberDto(String memberId, String memberPw) {
        this.memberId = memberId;
        this.memberPw = memberPw;
    }
}
