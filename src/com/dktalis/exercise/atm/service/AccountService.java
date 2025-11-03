package com.dktalis.exercise.atm.service;

import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;

import java.math.BigDecimal;

public interface AccountService {
    public Account addAccount(User user);
    public void upsertDebtAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount);
    public void updateRemainBalance(Account sourceAccount, boolean isDebtAmount, BigDecimal remainingBalance);
    public void updateTargetBalance(String targetUser, Account targetAccount, BigDecimal transferAmount);

}
