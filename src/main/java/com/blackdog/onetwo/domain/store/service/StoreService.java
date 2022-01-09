package com.blackdog.onetwo.domain.store.service;

import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.mapstruct.StoreMapstruct;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapstruct storeMapstruct;

    public StoreResult getStoreResult(Store store) {
        return storeMapstruct.storeToStoreResult(store);
    }

}
