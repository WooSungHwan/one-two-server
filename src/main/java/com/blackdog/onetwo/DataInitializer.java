package com.blackdog.onetwo;

import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.blackdog.onetwo.domain.review.enums.ReviewTag.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String kakaoId = UUID.randomUUID().toString().substring(20);
        Users users = userRepository.save(Users.of(passwordEncoder.encode(kakaoId), "test", kakaoId, "https://k.kakaocdn.net/dn/HkDIh/btrmlYT4Prd/kOZsI0MrBUEvwRzDJKV651/img_640x640.jpg"));
        Store store = storeRepository.save(Store.of(UUID.randomUUID().toString(), "옛손칼", "20211231", "경기도 의정부시 의정부1동 225-16", "경기도 의정부시 가능로136번길 9-10", "제육", "영업", 37.4962232, 127.0147866));
        Review review = reviewRepository.save(Review.of("content", store, users, List.of(GOOD_PICTURE, CHEAP, COFFEE_DELICIOUS)));

        log.info("user 생성 : {}", users.getId());
        log.info("store 생성 : {}", store.getId());
        log.info("review 생성 : {}", review.getId());
    }
}
