package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    public Map<String, User> user = new HashMap<>();

    public User getUser(String username) {
        return user.get(username);
    }

    public User adduser(String username) {
        User userEntity = new User();
        userEntity.setUsername(username);
        user.put(username, userEntity);
        return userEntity;
    }

}
