package com.noti.chatapp.dto.setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberDto {

    @NotBlank
    private String memberId;

    @NotBlank
    private String memberPw;

    @Builder
    public MemberDto(String memberId, String memberPw) {
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    public MemberDto() {

    }
}
