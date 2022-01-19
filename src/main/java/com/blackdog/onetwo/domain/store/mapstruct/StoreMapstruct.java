package com.blackdog.onetwo.domain.store.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {})
public interface StoreMapstruct {

    @Mappings({
            @Mapping(target = "location", expression = "java(new Double[] {store.getLongitude(), store.getLatitude()})")
    })
    StoreResult storeToStoreResult(Store store);
}
