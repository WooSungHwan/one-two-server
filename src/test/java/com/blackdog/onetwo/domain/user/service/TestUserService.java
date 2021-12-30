package com.blackdog.onetwo.domain.user.service;

import com.blackdog.onetwo.common.TestAbstractService;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import org.mockito.InjectMocks;

public class TestUserService extends TestAbstractService {

    @InjectMocks
    private UserRepository userRepository;

}
