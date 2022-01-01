package com.blackdog.onetwo.domain.store.repository;

import com.blackdog.onetwo.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
