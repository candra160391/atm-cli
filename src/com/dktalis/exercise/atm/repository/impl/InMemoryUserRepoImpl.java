package com.dktalis.exercise.atm.repository.impl;

import com.dktalis.exercise.atm.manager.UserManager;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.repository.UserRepository;

public class InMemoryUserRepoImpl implements UserRepository {

    private final UserManager userManager;

    public InMemoryUserRepoImpl(UserManager userManager){
        this.userManager = userManager;
    }

    @Override
    public User findByUserName(String username) {
        return userManager.getUserByUserName(username);
    }

    @Override
    public User findById(String id) {
        return userManager.getUserByUserId(id);
    }

    @Override
    public User addUser(User user) {
       return userManager.adduser(user);
    }
}
