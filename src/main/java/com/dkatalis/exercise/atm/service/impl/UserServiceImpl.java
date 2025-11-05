package com.dkatalis.exercise.atm.service.impl;

import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.repository.UserRepository;
import com.dkatalis.exercise.atm.service.UserService;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(String username){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(username);
        return userRepository.addUser(user);
    }
}
