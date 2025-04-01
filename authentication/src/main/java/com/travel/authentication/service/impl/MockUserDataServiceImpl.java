package com.travel.authentication.service.impl;

import com.travel.authentication.model.User;
import com.travel.authentication.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    @Component
    public class MockUserDataServiceImpl {
        private final Faker faker = new Faker();
        private final Random random = new Random();

        private final UserRepository userRepository;

        public MockUserDataServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @PostConstruct
        public void init() {
            generateMockOwnerData();
        }

        public void generateMockOwnerData() {
            List<User> users = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                User user = new User();
                user.setId(random.nextLong());
                user.setUsername(faker.internet().username());
                user.setPassword(faker.internet().password());
                user.setEmail(faker.internet().emailAddress());
                user.setCreatedAt(faker.date().toString());
                users.add(user);
                System.out.println(user);
            }

            userRepository.saveAll(users);
        }
    }
