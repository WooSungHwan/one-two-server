package com.blackdog.onetwo.configuration.security;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.user.entity.UserEntity;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String seq) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(Long.parseLong(seq))
                .orElseThrow(() -> new VerifyException(ErrorCode.USER_NOT_FOUND));

        return SecurityUser.of(userEntity);
    }
}
