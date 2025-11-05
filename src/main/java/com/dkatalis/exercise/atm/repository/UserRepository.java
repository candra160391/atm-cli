package com.dkatalis.exercise.atm.repository;

import com.dkatalis.exercise.atm.model.User;

public interface UserRepository {
    public User findByUserName(String username);
    public User findById(String id);
    public User addUser(User user);
}
