package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUser implements UserDetails {
    private Long seq;
    private String nickname;

    public static SecurityUser of(Long seq, String nickname) {
        return new SecurityUser(seq, nickname);
    }

    public static SecurityUser of(UserEntity userEntity) {
        return SecurityUser.of(userEntity.getId(), userEntity.getNickname());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
