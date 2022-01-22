package com.blackdog.onetwo.domain.user.repository;

import com.blackdog.onetwo.common.TestAbstractRepository;
import com.blackdog.onetwo.domain.user.UserFixture;
import com.blackdog.onetwo.domain.user.entity.UserTaste;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUserTasteRepository extends TestAbstractRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTasteRepository userTasteRepository;

    @Test
    void deleteUserTastesByUsersTest() {
        Users users = userRepository.findById(126L).get();

        UserTaste userTaste = UserTaste.builder()
                .users(users)
                .genderStep(GenderStep.FEMALE)
                .priceStep(PriceStep.IMPORTANT)
                .alcoholStep(AlcoholStep.HATE)
                .freshFoodStep(FreshFoodStep.HATE)
                .playStep(PlayStep.WITH_PLAY)
                .timeStep(TimeStep.AM)
                .build();

        UserTaste savedTaste = userTasteRepository.save(userTaste);

        userTasteRepository.deleteUserTastesByUsers(users);

        boolean exists = userTasteRepository.existsById(savedTaste.getId());
        assertThat(exists).isFalse();
    }

}
