package com.noti.chatapp.dto.setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    private String memberId;
    @Setter
    private String memberPw;

    @Builder
    public MemberDto(String memberId, String memberPw) {
        this.memberId = memberId;
        this.memberPw = memberPw;
    }
}
