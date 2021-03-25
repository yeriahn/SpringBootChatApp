package com.noti.chatapp.domain.setting;

import com.noti.chatapp.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String memberPw;

    public Member(Long id, String memberId, String memberPw) {
        this.id = id;
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    public void setUserPw(String memberPw) {
        this.memberPw = memberPw;
    }
}
