package com.blackdog.onetwo.domain.store.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {})
public interface StoreMapstruct {

    @Mappings({

    })
    StoreResult storeToStoreResult(Store store);
}
