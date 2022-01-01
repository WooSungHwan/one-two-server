package com.blackdog.onetwo.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "kakao_id", length = 20, nullable = false)
    private String kakaoId;

    public static Users of(Long id, String nickname, String kakaoId) {
        return new Users(id, nickname, kakaoId);
    }

    public static Users of(String nickname, String kakaoId) {
        return Users.of(null, nickname, kakaoId);
    }

}
