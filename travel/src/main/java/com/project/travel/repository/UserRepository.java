package com.project.travel.repository;

import com.project.travel.model.Entry;
import com.project.travel.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public void saveAll(List<User> newUsers) {
        users.addAll(newUsers);
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public User updateUser(User updatedUser) {
        return findById(updatedUser.getId()).map(user -> {
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return user;
        }).orElse(null);
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}
