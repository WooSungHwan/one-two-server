package com.blackdog.onetwo.domain.user.repository;

import com.blackdog.onetwo.domain.user.entity.UserTaste;
import com.blackdog.onetwo.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTasteRepository extends JpaRepository<UserTaste, Long> {

    void deleteUserTastesByUsers(Users users);

}
