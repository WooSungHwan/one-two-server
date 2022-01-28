package com.blackdog.onetwo.domain.user.entity;

import com.blackdog.onetwo.domain.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
@Entity
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "kakao_id", length = 50)
    private String kakaoId;

    @Column(name = "is_find_friends", columnDefinition = "boolean default false")
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

    public boolean isMe(Long seq) {
        return Objects.equals(seq, this.id);
    }

    public void signOut() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.nickname = "삭제된 유저";
        this.kakaoId = null;
        this.isFindFriends = null;
        this.profile = null;
        this.password = null;
    }
}
