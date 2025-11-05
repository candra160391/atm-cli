package com.dkatalis.exercise.atm.service;

import com.dkatalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dkatalis.exercise.atm.model.Account;
import com.dkatalis.exercise.atm.model.User;

import java.math.BigDecimal;

public interface AccountService {
    public Account addAccount(User user);
    public Account updateAccount(Account account);
    public Account upsertReceivableAccount(Account targetAccount, Account sourceAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType);
    public Account upsertDebtAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType);
}
