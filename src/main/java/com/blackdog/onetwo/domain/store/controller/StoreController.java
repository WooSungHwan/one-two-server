package com.blackdog.onetwo.domain.store.controller;

import com.blackdog.onetwo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
@RestController
public class StoreController {

    private final StoreService storeService;

}
