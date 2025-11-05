package com.dkatalis.exercise.atm.repository;

import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;

public interface AccountRepository {
    public Account findByUserId(String userId);
    public Account createNewAccount(User user);
    public Account updateAccount(Account account);
}
