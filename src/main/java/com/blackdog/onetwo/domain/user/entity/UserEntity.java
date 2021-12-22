package com.blackdog.onetwo.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "kakao_id", length = 20, nullable = false)
    private String kakaoId;

    public static UserEntity of(Long id, String nickname, String kakaoId) {
        return new UserEntity(id, nickname, kakaoId);
    }

    public static UserEntity of(String nickname, String kakaoId) {
        return UserEntity.of(null, nickname, kakaoId);
    }

}
