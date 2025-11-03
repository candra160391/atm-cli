package com.dktalis.exercise.atm.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private String accountId;
    private User user;
    private BigDecimal balance;
    private Map<String, Account> fromDebtAccount = new HashMap<>();
    private Map<String, Account> toDebtAccount = new HashMap<>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Map<String, Account> getFromDebtAccount() {
        return fromDebtAccount;
    }

    public void setFromDebtAccount(Map<String, Account> fromDebtAccount) {
        this.fromDebtAccount = fromDebtAccount;
    }

    public Map<String, Account> getToDebtAccount() {
        return toDebtAccount;
    }

    public void setToDebtAccount(Map<String, Account> toDebtAccount) {
        this.toDebtAccount = toDebtAccount;
    }
}
