package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager {

    private Map<String, Account> accountByUserId = new HashMap<>();
    private Map<String, Account> accountByAccountId = new HashMap<>();

    public Account getAccountByUserId(String userId) {
        return accountByUserId.get(userId);
    }

    public Account createNewAccount(User user) {
        Account accountEntity = new Account();
        accountEntity.setAccountId(UUID.randomUUID().toString());
        accountEntity.setUser(user);
        accountEntity.setBalance(BigDecimal.ZERO);
        upsertAccount(accountEntity);
        return accountEntity;
    }

    public void upsertAccount(Account account) {
        accountByUserId.put(account.getUser().getId(), account);
        accountByAccountId.put(account.getAccountId(), account);
    }
}
