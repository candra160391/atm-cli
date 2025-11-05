package com.dkatalis.exercise.atm.model;

import java.math.BigDecimal;

public class Account {
    private String accountId;
    private User user;
    private BigDecimal balance;
    private Account receivableAccount;
    private Account debtAccount;

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

    public Account getReceivableAccount() {
        return receivableAccount;
    }

    public void setReceivableAccount(Account receivableAccount) {
        this.receivableAccount = receivableAccount;
    }

    public Account getDebtAccount() {
        return debtAccount;
    }

    public void setDebtAccount(Account debtAccount) {
        this.debtAccount = debtAccount;
    }
}
