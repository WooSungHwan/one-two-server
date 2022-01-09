package com.blackdog.onetwo;

import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Users users = userRepository.save(Users.of("test", UUID.randomUUID().toString().substring(20)));
        Store store = storeRepository.save(Store.of(UUID.randomUUID().toString(), "20211231", "경기도 의정부시 의정부1동 225-16", "경기도 의정부시 가능로136번길 9-10", "음식점"));
        Review review = reviewRepository.save(Review.of("content", store, users));

        log.info("user 생성 : {}", users.getId());
        log.info("store 생성 : {}", store.getId());
        log.info("review 생성 : {}", review.getId());
    }
}
