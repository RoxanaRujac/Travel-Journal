package com.travel.authentication.repository;

import com.travel.authentication.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private static List<User> users = new ArrayList<>();

    public User findByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void saveAll(List<User> newUser) {
        users.addAll(newUser);
    }


}