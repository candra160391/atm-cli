package com.dktalis.exercise.atm.service;

import com.dktalis.exercise.atm.Enum.TransactionTypeEnum;
import com.dktalis.exercise.atm.model.Account;
import com.dktalis.exercise.atm.model.User;

import java.math.BigDecimal;

public interface AccountService {
    public Account addAccount(User user);
    public Account updateAccount(Account account);
    public Account upsertReceivableAccount(Account targetAccount, Account sourceAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType);
    public Account upsertDebtAccount(Account sourceAccount, Account targetAccount, BigDecimal debtAmount, TransactionTypeEnum transactionType);
}
