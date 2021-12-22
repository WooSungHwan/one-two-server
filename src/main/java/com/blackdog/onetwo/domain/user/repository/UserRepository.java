package com.blackdog.onetwo.domain.user.repository;

import com.blackdog.onetwo.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByKakaoId(String id);

    UserEntity findByKakaoId(String id);
}
