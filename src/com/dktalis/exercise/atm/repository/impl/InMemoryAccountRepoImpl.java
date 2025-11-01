package com.dktalis.exercise.atm.repository.impl;

import com.dktalis.exercise.atm.manager.AccountManager;
import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.repository.AccountRepository;
import java.math.BigDecimal;

public class InMemoryAccountRepoImpl implements AccountRepository {

    AccountManager accountManager;

    public InMemoryAccountRepoImpl(AccountManager accountManager){
        this.accountManager = accountManager;
    }

    @Override
    public Account findByUserId(String userId) {
        return accountManager.getAccountByUserId(userId);
    }

    @Override
    public Account addAccount(String userId) {
        return accountManager.addAccount(userId);
    }

    @Override
    public void updateBalance(String accountId, BigDecimal newBalance) {
        accountManager.updateBalance(accountId, newBalance);
    }
}
