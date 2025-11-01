package com.dktalis.exercise.atm.manager;

import com.dktalis.exercise.atm.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager {

    private Map<String, Account> accountByUserId = new HashMap<>();
    private Map<String, Account> accountByAccountId = new HashMap<>();

    public Account getAccountByAccountId(String accountId) {
        return accountByAccountId.get(accountId);
    }

    public Account getAccountByUserId(String userId) {
        return accountByUserId.get(userId);
    }

    public void updateBalance(String accountId, BigDecimal newBalance) {
        Account account = getAccountByAccountId(accountId);
        account.setBalance(newBalance);
        accountByAccountId.put(accountId, account);
        accountByUserId.put(account.getUserId(), account);
    }

    public Account addAccount(String userId) {
        Account accountEntity = new Account();
        accountEntity.setAccountId(UUID.randomUUID().toString());
        accountEntity.setUserId(userId);
        accountEntity.setBalance(BigDecimal.ZERO);
        accountByUserId.put(userId, accountEntity);
        accountByAccountId.put(accountEntity.getAccountId(), accountEntity);
        return accountEntity;
    }

}
