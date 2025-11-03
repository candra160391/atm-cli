package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private Map<String, User> userByUsername = new HashMap<>();
    private Map<String, User> userByUserId = new HashMap<>();

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
