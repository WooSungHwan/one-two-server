package com.blackdog.onetwo.domain.user.repository;

import com.blackdog.onetwo.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByKakaoId(String id);

    Users findByKakaoId(String id);
}
