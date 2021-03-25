package com.noti.chatapp.domain.setting;

import com.noti.chatapp.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String memberPw;

    @Builder
    public Member(Long id, String memberId, String memberPw) {
        this.id = id;
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    public void setUserPw(String memberPw) {
        this.memberPw = memberPw;
    }
}
