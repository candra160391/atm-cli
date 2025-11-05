package com.dkatalis.exercise.atm.repository.impl;

import com.dkatalis.exercise.atm.manager.AccountManager;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;
import com.dkatalis.exercise.atm.repository.AccountRepository;

public class InMemoryAccountRepoImpl implements AccountRepository {

    private final AccountManager accountManager;

    public InMemoryAccountRepoImpl(AccountManager accountManager){
        this.accountManager = accountManager;
    }

    @Override
    public Account findByUserId(String userId) {
        return accountManager.getAccountByUserId(userId);
    }

    @Override
    public Account createNewAccount(User user) {
        return accountManager.createNewAccount(user);
    }

    @Override
    public Account updateAccount(Account account) {
        accountManager.upsertAccount(account);
        return account;
    }
}
