package com.blackdog.onetwo.domain.user.repository;

import com.blackdog.onetwo.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByIdAndDeletedFalse(Long id);

    boolean existsByKakaoId(String id);

    Users findByKakaoId(String id);
}
