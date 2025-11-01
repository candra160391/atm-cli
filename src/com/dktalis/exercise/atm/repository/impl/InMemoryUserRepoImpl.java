package com.dktalis.exercise.atm.repository.impl;

import com.dktalis.exercise.atm.manager.UserManager;
import com.dktalis.exercise.atm.model.User;
import com.dktalis.exercise.atm.repository.UserRepository;

public class InMemoryUserRepoImpl implements UserRepository {

    UserManager userManager;

    public InMemoryUserRepoImpl(UserManager userManager){
        this.userManager = userManager;
    }

    @Override
    public User findByUserName(String username) {
        return userManager.getUser(username);
    }

    @Override
    public User addUser(String username) {
       return userManager.adduser(username);
    }
}
