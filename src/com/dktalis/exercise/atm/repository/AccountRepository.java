package com.dktalis.exercise.atm.repository;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;

public interface AccountRepository {
    public Account findByUserId(String userId);
    public Account createNewAccount(User user);
    public Account updateAccount(Account account);
}
