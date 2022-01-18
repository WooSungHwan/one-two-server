package com.blackdog.onetwo.domain.user.entity;

import com.blackdog.onetwo.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@DynamicInsert
@Table(name = "users")
@Entity
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "kakao_id", length = 20, nullable = false)
    private String kakaoId;

    @Column(name = "is_find_friends", nullable = false, columnDefinition = "boolean default false")
    private Boolean isFindFriends;

    @Column(name = "profile")
    private String profile;

    private Users(Long id, String password, String nickname, String kakaoId, String profile) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.profile = profile;
    }

    public static Users of(Long id, String password, String nickname, String kakaoId, String profile) {
        return new Users(id, password, nickname, kakaoId, profile);
    }

    public static Users of(String password, String nickname, String kakaoId, String profile) {
        return of(null, password, nickname, kakaoId, profile);
    }

    public void onFindFriends() {
        this.isFindFriends = true;
    }

    public void offFindFriends() {
        this.isFindFriends = false;
    }

}
