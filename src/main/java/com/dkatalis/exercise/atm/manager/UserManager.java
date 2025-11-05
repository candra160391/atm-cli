package com.dkatalis.exercise.atm.manager;

import com.dkatalis.exercise.atm.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private final Map<String, User> userByUsername = new HashMap<>();
    private final Map<String, User> userByUserId = new HashMap<>();

    public User getUserByUserName(String username) {
        return userByUsername.get(username);
    }

    public User getUserByUserId(String userId) {
        return userByUserId.get(userId);
    }

    public User adduser(User user) {
        userByUsername.put(user.getName(), user);
        userByUserId.put(user.getId(), user);
        return user;
    }
}
