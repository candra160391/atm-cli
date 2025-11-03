package com.dktalis.exercise.atm.repository;

import com.dktalis.exercise.atm.model.User;

public interface UserRepository {
    public User findByUserName(String username);
    public User findById(String id);
    public User addUser(User user);
}
